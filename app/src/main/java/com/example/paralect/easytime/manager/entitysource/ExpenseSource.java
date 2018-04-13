package com.example.paralect.easytime.manager.entitysource;

import android.support.annotation.NonNull;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.ExpenseUnit;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.utils.ExpenseUtil;
import com.example.paralect.easytime.utils.Logger;
import com.paralect.database.request.ExpenseRequestORM;
import com.paralect.database.request.FileRequestORM;
import com.paralect.database.request.MaterialRequestORM;
import com.paralect.database.request.OrderRequestORM;
import com.paralect.database.request.ProjectRequestORM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.paralect.easytime.model.Constants.EXPENSE_ID;
import static com.example.paralect.easytime.model.Constants.FILE_ID;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.MATERIAL;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.OTHER;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

public class ExpenseSource extends EntitySource{

    public void updateExpense(Expense expense) throws Exception {
        ExpenseRequestORM saveRequest = new ExpenseRequestORM();
        saveRequest.setEntity(expense);
        dataSource.update(saveRequest);
    }

    public List<Expense> getDefaultExpenses(String jobId) {
        return Expense.getDefaultExpenses(EasyTimeApplication.getContext(), jobId);
    }

    /**
     * Using for query expenses by searching name and expense type
     * <p>
     * Doesn't work in case of case sensitive: qb.distinct().selectColumns("name");
     *
     * @param jobId       is field of Job object
     * @param searchQuery for searching in name field
     * @param expenseType
     * @return list of expenses
     */
    private List<Expense> getExpenses(String jobId, String searchQuery, @ExpenseUnit.Type String expenseType) throws Exception {
        ExpenseRequestORM request = new ExpenseRequestORM();
        request.queryForListExpense(jobId, searchQuery, expenseType);
        return dataSource.getList(request);
    }

    public List<Expense> getOtherExpenses(String jobId, String searchQuery) throws Exception {
        return getExpenses(jobId, searchQuery, OTHER);
    }

    public List<Expense> getMaterialExpenses(String jobId) throws Exception {
        return getExpenses(jobId, null, MATERIAL);
    }

    public long getTotalExpensesCount(String jobId) {
        try {
            JobSource jobSource = new JobSource();
            long totalCount = countExpenses(jobId);

            ProjectRequestORM projectRequest = new ProjectRequestORM();
            projectRequest.queryForId(jobId);
            Project project = dataSource.get(projectRequest);

            if (project != null) {
                List<Object> objects = jobSource.getObjects(project.getObjectIds());
                for (Object o : objects) {
                    totalCount += getTotalExpensesCount(o.getId());
                }
            } else {

                OrderRequestORM orderRequest = new OrderRequestORM();
                orderRequest.queryForId(jobId);
                Order order = dataSource.get(orderRequest);

                if (order != null) {
                    List<Object> objects = jobSource.getObjects(order.getObjectIds());
                    for (Object o : objects) {
                        totalCount += getTotalExpensesCount(o.getId());
                    }
                }
            }
            return totalCount;
        } catch (Exception e) {
            Logger.e(e);
            return 0;
        }
    }

    /**
     * @param jobId is field of Job object
     * @return total count of expenses for Job object with jobId field
     */
    public long countExpenses(String jobId) throws Exception {
        ExpenseRequestORM expenseRequest = new ExpenseRequestORM();
        expenseRequest.queryCountOfJobs(jobId);
        return dataSource.count(expenseRequest);
    }

    public List<Expense> getAllExpenses(String jobId) throws Exception{
        return getAllExpenses(jobId, null);
    }

    /**
     * Using for query expenses by date and jobId
     *
     * @param jobId is field of Job object
     * @param date  should be in "yyyy-MM-dd" format
     * @return list of expenses
     */
    public List<Expense> getAllExpenses(String jobId, String date) throws Exception{
        List<Expense> allExpenses = new ArrayList<>();
        try {
            List<String> ids = new ArrayList<>();
            ids.add(jobId);

            ProjectRequestORM projectRequest = new ProjectRequestORM();
            projectRequest.queryForId(jobId);
            Project project = dataSource.get(projectRequest);

            if (project != null) {
                Logger.d(TAG, "its a project, query should be also performed for all objects");
                ids.addAll(Arrays.asList(project.getObjectIds()));
            }

            OrderRequestORM orderRequest = new OrderRequestORM();
            orderRequest.queryForId(jobId);
            Order order = dataSource.get(orderRequest);

            if (order != null) {
                Logger.d(TAG, "its an order, query should be also performed for all objects");
                ids.addAll(Arrays.asList(order.getObjectIds()));
            }

            ExpenseRequestORM expenseRequest = new ExpenseRequestORM();
            MaterialRequestORM materialRequest = new MaterialRequestORM();

            TypeSource typeSource = new TypeSource();
            for (String id : ids) {

                expenseRequest.queryForListExpense(jobId, date);
                List<Expense> foundExpense = dataSource.getList(expenseRequest);

                Logger.d(TAG, String.format("totally found %s expenses", foundExpense.size()));

                for (final Expense exp : foundExpense)
                    setValueWithUnit(typeSource, exp, materialRequest);

                allExpenses.addAll(foundExpense);
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return allExpenses;
    }

    /**
     * Create value with unit description
     *
     * @param expense
     * @param materialRequest
     */
    private void setValueWithUnit(final TypeSource typeSource, @NonNull final Expense expense, final MaterialRequestORM materialRequest) {
        expense.setValueWithUnitName(new Expense.ExpenseValueWithUnit(expense.getValue()) {
            @Override
            public String getMaterialUnit() {
                try {
                    if (expense != null && materialRequest != null) {
                        String materialId = expense.getMaterialId();
                        Logger.d(TAG, String.format("material id for curr expense = %s", materialId));
                        materialRequest.queryForId(materialId);
                        Material material = dataSource.get(materialRequest);
                        if (material != null) {
                            com.example.paralect.easytime.model.Type t = typeSource.getType(material.getUnitId());
                            if (t != null)
                                return getValue() + " " + t.getName();
                        }
                    }
                } catch (Exception e) {
                    Logger.e(e);
                }
                return super.getMaterialUnit();
            }
        });
    }

    public String getUnitName(final TypeSource typeSource, @ExpenseUnit.Type String type, final Material material) {
        return ExpenseUtil.getUnit(type, new Expense.ExpenseUnitName() {
            @Override
            public String getMaterialUnit() {
                String unitName = super.getMaterialUnit();
                com.example.paralect.easytime.model.Type t = typeSource.getType(material.getUnitId());
                if (t != null)
                    unitName = t.getName();
                return unitName;
            }
        });
    }

    /**
     * Save and retrieve last Expense from the table
     *
     * @param expense that will be saved
     * @return saved Expense
     */
    public Expense saveAndGetExpense(Expense expense) throws Exception {
        ExpenseRequestORM saveRequest = new ExpenseRequestORM();
        saveRequest.setEntity(expense);
        dataSource.saveOrUpdate(saveRequest);

        ExpenseRequestORM getRequest = new ExpenseRequestORM();
        getRequest.queryForLast();
        return dataSource.get(getRequest);
    }

    public void deleteExpense(Expense expense) throws Exception{
        File file = getFile(expense);
        if (file != null) {
            java.io.File imageFile = file.getImageFile();
            boolean isDeleted = imageFile.delete();
            Logger.d("file deleted = " + isDeleted);
        }

        ExpenseRequestORM request = new ExpenseRequestORM();
        request.setEntity(expense);
        dataSource.delete(request);
    }

    public void saveAndGetExpense(String jobId, Material material, int countOfMaterials) throws Exception {

        Expense expense = Expense.createMaterialExpense(jobId, material.getName(), material.getMaterialId(), countOfMaterials);
        material.setStockQuantity(material.getStockQuantity() - countOfMaterials);

        // TODO Should we count the price right here ???
        ExpenseRequestORM expenseRequest = new ExpenseRequestORM();
        expenseRequest.setEntity(expense);
        dataSource.saveOrUpdate(expenseRequest);

        MaterialRequestORM materialRequest = new MaterialRequestORM();
        materialRequest.setEntity(material);
        dataSource.update(materialRequest);
    }

    // region File
    public File getFile(Expense expense) throws Exception {
        FileRequestORM fileRequest = new FileRequestORM();
        fileRequest.queryForFirst(EXPENSE_ID);
        return dataSource.get(fileRequest);
    }

    public List<File> getFilesByExpenseId(String expenseId) throws Exception {
        FileRequestORM fileRequest = new FileRequestORM();
        fileRequest.queryWhere(FILE_ID, expenseId);
        return dataSource.getList(fileRequest);
    }
}
