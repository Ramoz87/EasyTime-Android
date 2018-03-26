package com.example.paralect.easytime.manager;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.ExpenseUnit;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.ProjectType;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.model.User;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.utils.ExpenseUtil;
import com.example.paralect.easytime.utils.Logger;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.paralect.easytimedataormlite.DatabaseHelperORMLite;
import com.paralect.easytimedataormlite.request.ExpenseRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.paralect.easytime.model.ExpenseUnit.Type.MATERIAL;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.OTHER;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.TIME;
import static com.example.paralect.easytime.model.Type.TypeName.STATUS;
import static com.example.paralect.easytime.utils.CalendarUtils.SHORT_DATE_FORMAT;
import static com.paralect.easytimedataormlite.model.ExpenseEntity.JOB_ID;

/**
 * Created by alexei on 26.12.2017.
 */

public final class EasyTimeManager {
    private static final String TAG = EasyTimeManager.class.getSimpleName();

    private volatile static EasyTimeManager instance;
    private DatabaseHelperORMLite dataSource;

    /**
     * Returns singleton class instance
     */
    public static EasyTimeManager getInstance() {
        EasyTimeManager localInstance = instance;

        if (localInstance == null) {
            synchronized (EasyTimeManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EasyTimeManager();
                }
            }
        }
        return localInstance;
    }

    private EasyTimeManager() {
        if (dataSource == null)
            dataSource = new DatabaseHelperORMLite(EasyTimeApplication.getContext());
    }

    public DatabaseHelperORMLite getHelper() {
        return dataSource;
    }

    public void updateJob(Job job) {
//        try {
//            @ProjectType.Type int projectType = job.getProjectType();
//            if (projectType == ProjectType.Type.TYPE_OBJECT) {
//                dataSource.update(Object.class, (Object)job);
//            } else if (projectType == ProjectType.Type.TYPE_PROJECT) {
//                dataSource.update(Project.class, (Project)job);
//            } else if (projectType == ProjectType.Type.TYPE_ORDER) {
//                dataSource.update(Order.class, (Order)job);
//            }
//        } catch (SQLException e) {
//            Logger.e(TAG, e.getMessage());
//        }
    }

    // region TypeEntity
    public Type getType(String typeId) {
        return new Type();
    }

    public List<Type> getStatuses() {
        return getTypes(STATUS, "");
    }

    public List<Type> getTypes() {
        return getTypes(null, "");
    }

    public List<Type> getTypes(@Type.TypeName String type, String searchName) {
        return new ArrayList<>();
    }
    // endregion

    public List<Contact> getContacts(Customer customer) {
        return new ArrayList<>();
    }

    public Address getAddress(Customer customer) {
        return new Address();
    }

    public Type getStatus(Job job) {
        return new Type();
    }

    // region Jobs
    public List<Job> getAllJobs() {
        return new ArrayList<>();
    }

    public List<Object> getObjects(Customer customer) throws SQLException {
        return new ArrayList<>();
    }

    public List<Order> getOrders(Customer customer) throws SQLException {
        return new ArrayList<>();
    }

    public List<Project> getProjects(Customer customer) throws SQLException {
        return new ArrayList<>();
    }

    public List<Integer> getJobTypes(Customer customer) {
        List<Integer> types = new ArrayList<>();
        try {
            String id = customer.getId();
            if (dataSource.getObjectDao().queryBuilder().where().eq("customerId", id).countOf() != 0)
                types.add(ProjectType.Type.TYPE_OBJECT);
            if (dataSource.getOrderDao().queryBuilder().where().eq("customerId", id).countOf() != 0)
                types.add(ProjectType.Type.TYPE_ORDER);
            if (dataSource.getProjectDao().queryBuilder().where().eq("customerId", id).countOf() != 0)
                types.add(ProjectType.Type.TYPE_PROJECT);
            return types;
        } catch (SQLException exc) {
            Logger.e(exc);
            return types;
        }
    }

    public long getJobCount(Customer customer, @ProjectType.Type int projectType) {
        try {
            Dao dao;
            if (projectType == ProjectType.Type.TYPE_OBJECT) dao = dataSource.getObjectDao();
            else if (projectType == ProjectType.Type.TYPE_PROJECT) dao = dataSource.getProjectDao();
            else if (projectType == ProjectType.Type.TYPE_ORDER) dao = dataSource.getOrderDao();
            else return 0L;
            return dao.queryBuilder().where().eq("customerId", customer.getId()).countOf();
        } catch (SQLException exc) {
            Logger.e(exc);
            return 0L;
        }
    }

    public <T extends Job> List<T> getJobs(Dao<T, String> dao, Customer customer, String query, String date) throws SQLException {

        return new ArrayList<>();
    }

    public List<Job> getJobs(Customer customer, String query, String date) {
        return new ArrayList<>();
    }

    private <JOB extends Job> List<JOB> getList(Dao<JOB, String> dao, String customerId, String query, String date) throws SQLException {
        QueryBuilder<JOB, String> qb = dao.queryBuilder();

        boolean hasCustomerId = !TextUtils.isEmpty(customerId);
        boolean hasQuery = !TextUtils.isEmpty(query);
        boolean hasDate = !TextUtils.isEmpty(date);

        Where where = null;
        if (hasCustomerId) {
            where = qb.where().eq("customerId", customerId);
        }

        if (hasQuery) {
            if (where == null) where = qb.where();
            else where.and();

            where.like("name", "%" + query + "%")
                    .or().raw("CAST(number AS TEXT) LIKE '%" + query + "%'");
        }

        if (hasDate) {
            Date time = CalendarUtils.dateFromString(date, SHORT_DATE_FORMAT);
            if (where == null) where = qb.where();
            else where.and();

            where.le("date", time.getTime());
        }

        List<JOB> objects = qb.query();
        for (JOB item : objects) { // populating members
            List<User> members = getMembers(item);
            item.setMembers(members);
        }
        return objects;
    }

    public List<User> getMembers(Job job) throws SQLException {
        return new ArrayList<>();
    }
    // endregion

    public List<Material> getMaterials(String query) throws SQLException {
        return new ArrayList<>();
    }

    public List<Customer> getCustomers(String query) {
        return new ArrayList<>();
    }

    public void updateMaterial(Material material) {

    }

    public List<Material> getMyMaterials() {
        return new ArrayList<>();
    }

    public void deleteMyMaterials() {

    }

    public List<Expense> getDefaultExpenses(String jobId) {
        return Expense.getDefaultExpenses(EasyTimeApplication.getContext(), jobId);
    }

    /**
     * Using for query expenses by searching name and expense type
     * <p>
     * Doesn't work in case of case sensitive: qb.distinct().selectColumns("name");
     *
     * @param jobId       is field of JobEntity object
     * @param searchQuery for searching in name field
     * @param expenseType
     * @return list of expenses
     */
    private List<Expense> getExpenses(String jobId, String searchQuery, @ExpenseUnit.Type String expenseType) throws SQLException {
        ExpenseRequest request = new ExpenseRequest().listExpenseRequest(dataSource, jobId, searchQuery, expenseType);
        return dataSource.getList(request);
    }

    public List<Expense> getOtherExpenses(String jobId, String searchQuery) throws SQLException {
        return getExpenses(jobId, searchQuery, OTHER);
    }

    public List<Expense> getMaterialExpenses(String jobId) throws SQLException {
        return getExpenses(jobId, null, MATERIAL);
    }

    public List<Expense> getTimeExpenses(String jobId) throws SQLException {
        return getExpenses(jobId, null, TIME);
    }

    public long getTotalExpensesCount(String jobId) {
        return 1;
    }

    /**
     * @param jobId is field of JobEntity object
     * @return total count of expenses for JobEntity object with jobId field
     */
    public <P> long countExpenses(P jobId) throws SQLException {
        Where where = dataSource.getExpenseDao().queryBuilder().where().eq(JOB_ID, jobId);
        return where.countOf();
    }

    public List<Expense> getAllExpenses(String jobId) {
        return getAllExpenses(jobId, null);
    }

    /**
     * Using for query expenses by date and jobId
     *
     * @param jobId is field of JobEntity object
     * @param date  should be in "yyyy-MM-dd" format
     * @return list of expenses
     */
    public List<Expense> getAllExpenses(String jobId, String date) {
        return new ArrayList<>();
    }

    public List<Expense> getExpenses(String jobId, String date) throws SQLException {
        return new ArrayList<>();
    }

    /**
     * Create value with unit description
     *
     * @param expense
     * @param materialDao
     */
    private void setValueWithUnit(@NonNull final Expense expense, final Dao<Material, String> materialDao) {
        expense.setValueWithUnitName(new Expense.ExpenseValueWithUnit(expense.getValue()) {
            @Override
            public String getMaterialUnit() {
                try {
                    if (expense != null && materialDao != null) {
                        String materialId = expense.getMaterialId();
                        Logger.d(TAG, String.format("material id for curr expense = %s", materialId));
                        Material material = materialDao.queryForId(materialId);
                        if (material != null) {
                            com.example.paralect.easytime.model.Type t = getType(material.getUnitId());
                            if (t != null)
                                return getValue() + " " + t.getName();
                        }
                    }
                } catch (SQLException e) {
                    Logger.e(e);
                }
                return super.getMaterialUnit();
            }
        });
    }

    public String getUnitName(@ExpenseUnit.Type String type, final Material material) {
        return ExpenseUtil.getUnit(type, new Expense.ExpenseUnitName() {
            @Override
            public String getMaterialUnit() {
                String unitName = super.getMaterialUnit();
                com.example.paralect.easytime.model.Type t = getType(material.getUnitId());
                if (t != null)
                    unitName = t.getName();
                return unitName;
            }
        });
    }

    /**
     * Save and retrieve last ExpenseEntity from the table
     *
     * @param expense that will be saved
     * @return saved ExpenseEntity
     */
    public Expense saveAndGetExpense(Expense expense) throws SQLException {
        ExpenseRequest saveRequest = new ExpenseRequest();
        saveRequest.setInternalEntity(expense);
        dataSource.save(saveRequest);

        ExpenseRequest getRequest = new ExpenseRequest().getExpenseRequest(dataSource);
        return dataSource.get(getRequest);
    }

    public List<Object> getObjects(String[] ids) {
        return new ArrayList<>();
    }

    public void deleteExpense(Expense expense) {

    }

    public void saveAndGetExpense(String jobId, Material material, int countOfMaterials) throws SQLException {

    }

    // region FileEntity
    public File getFile(Expense expense) throws SQLException {
        return new File();
    }

    public List<File> getFilesByExpenseId(Long expenseId) throws SQLException {
        return new ArrayList<>();
    }

    public List<File> getFiles(Job job) throws SQLException {
        return new ArrayList<>();
    }

    public void saveFile(File file) throws SQLException {

    }

    public File saveFileAndGet(File file) throws SQLException {
        return new File();
    }

    public void deleteFile(File file) throws SQLException {

    }
    // endregion

    public User getUser(String userId) {
        return new User();
    }

    public User getRandomUser() {
        return new User();
    }
}
