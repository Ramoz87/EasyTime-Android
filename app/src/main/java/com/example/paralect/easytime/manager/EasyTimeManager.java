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
import com.example.paralect.easytime.model.JobWithAddress;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.ProjectType;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.model.User;
import com.example.paralect.easytime.utils.ExpenseUtil;
import com.example.paralect.easytime.utils.Logger;
//import com.j256.ormlite.dao.Dao;
//import com.j256.ormlite.stmt.QueryBuilder;
//import com.j256.ormlite.stmt.UpdateBuilder;
//import com.j256.ormlite.stmt.Where;
import com.paralect.datasource.core.EntityRequest;
import com.paralect.easytimedataormlite.DatabaseHelperORMLite;
import com.paralect.easytimedataormlite.model.FileEntity;
import com.paralect.easytimedataormlite.model.JobEntity;
import com.paralect.easytimedataormlite.request.AddressRequest;
import com.paralect.easytimedataormlite.request.BaseJobRequest;
import com.paralect.easytimedataormlite.request.ContactRequest;
import com.paralect.easytimedataormlite.request.CustomerRequest;
import com.paralect.easytimedataormlite.request.ExpenseRequest;
import com.paralect.easytimedataormlite.request.FileRequest;
import com.paralect.easytimedataormlite.request.MaterialRequest;
import com.paralect.easytimedataormlite.request.ObjectRequest;
import com.paralect.easytimedataormlite.request.OrderRequest;
import com.paralect.easytimedataormlite.request.ProjectRequest;
import com.paralect.easytimedataormlite.request.TypeRequest;
import com.paralect.easytimedataormlite.request.UserRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.paralect.easytime.model.ExpenseUnit.Type.MATERIAL;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.OTHER;
import static com.example.paralect.easytime.model.Type.TypeName.STATUS;
import static com.example.paralect.easytime.utils.CalendarUtils.SHORT_DATE_FORMAT;
import static com.paralect.easytimedataormlite.model.ExpenseEntity.EXPENSE_ID;

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

    public void updateJob(Job job) {
        try {
            @ProjectType.Type int projectType = job.getProjectType();
            if (projectType == ProjectType.Type.TYPE_OBJECT) {
                ObjectRequest request = new ObjectRequest();
                request.setEntity((Object) job);
                dataSource.update(request);
            } else if (projectType == ProjectType.Type.TYPE_PROJECT) {
                ProjectRequest request = new ProjectRequest();
                request.setEntity((Project) job);
                dataSource.update(request);
            } else if (projectType == ProjectType.Type.TYPE_ORDER) {
                OrderRequest request = new OrderRequest();
                request.setEntity((Order) job);
                dataSource.update(request);
            }
        } catch (SQLException e) {
            Logger.e(TAG, e.getMessage());
        }
    }

    // region Type
    public Type getType(String typeId) {
        Type type = null;
        try {
            TypeRequest typeRequest = new TypeRequest();
            typeRequest.queryForId(dataSource, typeId);
            type = dataSource.get(typeRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return type;
    }

    public List<Type> getStatuses() {
        return getTypes(STATUS, "");
    }

    public List<Type> getTypes() {
        return getTypes(null, "");
    }

    public List<Type> getTypes(@Type.TypeName String type, String searchName) {
        try {
            TypeRequest typeRequest = new TypeRequest();
            typeRequest.queryForList(dataSource, type, searchName);
            List<Type> list = dataSource.getList(typeRequest);
            return list;
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }
    // endregion

    public List<Contact> getContacts(Customer customer) {
        try {
            ContactRequest contactRequest = new ContactRequest();
            contactRequest.queryForEq(dataSource, customer.getId());
            return dataSource.getList(contactRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
            return new ArrayList<>();
        }
    }

    public Address getAddress(Customer customer) {
        try {
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.queryForId(dataSource, customer.getAddressId());
            return dataSource.get(addressRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }

//    public Type getStatus(Job job) {
//        try {
//            Dao<Type, String> dao = dataSource.getTypeDao();
//            List<Type> results = dao.queryBuilder().where().idEq(job.getStatusId()).query();
//            if (!CollectionUtil.isEmpty(results)) return results.get(0);
//            else return null;
//        } catch (SQLException exc) {
//            Logger.e(exc);
//            return null;
//        }
//    }

    // region Jobs
    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        try {

            ObjectRequest objectRequest = new ObjectRequest();
            OrderRequest orderRequest = new OrderRequest();
            ProjectRequest projectRequest = new ProjectRequest();

            objectRequest.queryForList(dataSource, null, null, null);
            orderRequest.queryForList(dataSource, null, null, null);
            projectRequest.queryForList(dataSource, null, null, null);

            List<Object> objects = dataSource.getList(objectRequest);
            List<Order> orders = dataSource.getList(orderRequest);
            List<Project> projects = dataSource.getList(projectRequest);

            jobs.addAll(objects);
            jobs.addAll(orders);
            jobs.addAll(projects);

            CustomerRequest customerRequest = new CustomerRequest();
            for (Job job : jobs) {
                String customerId = job.getCustomerId();
                customerRequest.queryForId(dataSource, customerId);
                Customer customer = dataSource.get(customerRequest);
                job.setCustomer(customer);
            }

            AddressRequest addressRequest = new AddressRequest();
            for (Job job : jobs) {
                if (job instanceof JobWithAddress) {
                    JobWithAddress jobWithAddress = (JobWithAddress) job;
                    addressRequest.queryForId(dataSource, jobWithAddress.getAddressId());
                    Address address = dataSource.get(addressRequest);
                    jobWithAddress.setAddress(address);
                }
            }

            TypeRequest typeRequest = new TypeRequest();
            for (Job job : jobs) {
                String statusId = job.getStatusId();
                typeRequest.queryForId(dataSource, statusId);
                Type status = dataSource.get(typeRequest);
                job.setStatus(status);
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return jobs;
    }

    public List<Object> getObjects(Customer customer) throws SQLException {
        ObjectRequest objectRequest = new ObjectRequest();
        return getJobs(objectRequest, customer, null, null);
    }

    public List<Order> getOrders(Customer customer) throws SQLException {
        OrderRequest orderRequest = new OrderRequest();
        return getJobs(orderRequest, customer, null, null);
    }

    public List<Project> getProjects(Customer customer) throws SQLException {
        ProjectRequest projectRequest = new ProjectRequest();
        return getJobs(projectRequest, customer, null, null);
    }

    public List<Integer> getJobTypes(Customer customer) {
        List<Integer> types = new ArrayList<>();
        try {
            String id = customer.getId();
            ObjectRequest objectRequest = new ObjectRequest();
            OrderRequest orderRequest = new OrderRequest();
            ProjectRequest projectRequest = new ProjectRequest();

            objectRequest.queryCountForCustomers(dataSource, id);
            orderRequest.queryCountForCustomers(dataSource, id);
            projectRequest.queryCountForCustomers(dataSource, id);

            if (dataSource.count(objectRequest) != 0)
                types.add(ProjectType.Type.TYPE_OBJECT);
            if (dataSource.count(orderRequest) != 0)
                types.add(ProjectType.Type.TYPE_ORDER);
            if (dataSource.count(projectRequest) != 0)
                types.add(ProjectType.Type.TYPE_PROJECT);

            return types;
        } catch (SQLException exc) {
            Logger.e(exc);
            return types;
        }
    }

    public long getJobCount(Customer customer, @ProjectType.Type int projectType) {
        try {

            String id = customer.getId();

            if (projectType == ProjectType.Type.TYPE_OBJECT) {
                ObjectRequest objectRequest = new ObjectRequest();
                objectRequest.queryCountForCustomers(dataSource, id);
                return dataSource.count(objectRequest);

            } else if (projectType == ProjectType.Type.TYPE_PROJECT) {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.queryCountForCustomers(dataSource, id);
                return dataSource.count(orderRequest);

            } else if (projectType == ProjectType.Type.TYPE_ORDER) {
                ProjectRequest projectRequest = new ProjectRequest();
                projectRequest.queryCountForCustomers(dataSource, id);
                return dataSource.count(projectRequest);
            } else return 0L;

        } catch (SQLException exc) {
            Logger.e(exc);
            return 0L;
        }
    }

    public <T extends Job> List<T> getJobs(BaseJobRequest request, Customer customer, String query, String date) throws SQLException {

        String customerId = customer == null ? "" : customer.getId();
        request.queryForList(dataSource, customerId, query, date);
        List<T> jobs = dataSource.getList(request);

        if (customer == null) {
            CustomerRequest customerRequest = new CustomerRequest();
            for (Job job : jobs) {
                customerId = job.getCustomerId();
                customerRequest.queryForId(dataSource, customerId);
                customer = dataSource.get(customerRequest);
                job.setCustomer(customer);
            }
        }

        AddressRequest addressRequest = new AddressRequest();
        for (Job job : jobs) {
            if (job instanceof JobWithAddress) {
                JobWithAddress jobWithAddress = (JobWithAddress) job;
                addressRequest.queryForId(dataSource, jobWithAddress.getAddressId());
                Address address = dataSource.get(addressRequest);
                jobWithAddress.setAddress(address);
            }
        }

        TypeRequest typeRequest = new TypeRequest();
        for (Job job : jobs) {
            typeRequest.queryForId(dataSource, job.getStatusId());
            Type status = dataSource.get(typeRequest);
            job.setStatus(status);
        }
        return jobs;
    }

    public List<Job> getJobs(Customer customer, String query, String date) {
        List<Job> jobs = new ArrayList<>();
        try {

            String customerId = customer == null ? "" : customer.getId();

            ObjectRequest objectRequest = new ObjectRequest();
            OrderRequest orderRequest = new OrderRequest();
            ProjectRequest projectRequest = new ProjectRequest();

            objectRequest.queryForList(dataSource, customerId, query, date);
            orderRequest.queryForList(dataSource, customerId, query, date);
            projectRequest.queryForList(dataSource, customerId, query, date);

            List<Object> objects = dataSource.getList(objectRequest);
            List<Order> orders = dataSource.getList(orderRequest);
            List<Project> projects = dataSource.getList(projectRequest);

            jobs.addAll(objects);
            jobs.addAll(orders);
            jobs.addAll(projects);

            if (customer == null) {
                CustomerRequest customerRequest = new CustomerRequest();
                for (Job job : jobs) {
                    customerId = job.getCustomerId();
                    customerRequest.queryForId(dataSource, customerId);
                    customer = dataSource.get(customerRequest);
                    job.setCustomer(customer);
                }
            }

            AddressRequest addressRequest = new AddressRequest();
            for (Job job : jobs) {
                if (job instanceof JobWithAddress) {
                    JobWithAddress jobWithAddress = (JobWithAddress) job;
                    addressRequest.queryForId(dataSource, jobWithAddress.getAddressId());
                    Address address = dataSource.get(addressRequest);
                    jobWithAddress.setAddress(address);
                }
            }

            TypeRequest typeRequest = new TypeRequest();
            for (Job job : jobs) {
                typeRequest.queryForId(dataSource, job.getStatusId());
                Type status = dataSource.get(typeRequest);
                job.setStatus(status);
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return jobs;
    }

    public List<User> getMembers(Job job) throws SQLException {
        String[] ids = job.getMemberIds();
        if (ids == null || ids.length == 0) return null;
        List<User> users = new ArrayList<>();
        UserRequest userRequest = new UserRequest();

        for (String id : ids) {
            userRequest.queryForId(dataSource, id);
            User user = dataSource.get(userRequest);
            users.add(user);
        }
        return users;
    }
    // endregion

    public List<Material> getMaterials(String query) throws SQLException {
        MaterialRequest materialRequest = new MaterialRequest();
        materialRequest.queryForSearch(dataSource, query);
        return dataSource.getList(materialRequest);
    }

    public List<Customer> getCustomers(String query) {
        List<Customer> customers = new ArrayList<>();
        try {

            CustomerRequest customerRequest = new CustomerRequest();

            if (TextUtils.isEmpty(query))
                customerRequest.queryForAll(dataSource);
            else
                customerRequest.queryForSearch(dataSource, query);

            customers = dataSource.getList(customerRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return customers;
    }

    public void updateMaterial(Material material) {
        try {
            MaterialRequest materialRequest = new MaterialRequest();
            materialRequest.setEntity(material);
            dataSource.update(materialRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
    }

    public List<Material> getMyMaterials() {
        List<Material> materials = new ArrayList<>();
        try {
            MaterialRequest materialRequest = new MaterialRequest();
            materialRequest.queryForAdded(dataSource);
            List<Material> myMaterials = dataSource.getList(materialRequest);
            materials.addAll(myMaterials);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return materials;
    }

    // TODO: Have no idea how to add UpdateBuilder to requests
    public void deleteMyMaterials() {
//        try {
//            Dao<Material, String> dao = dataSource.getMaterialDao();
//            UpdateBuilder<Material, String> ub = dao.updateBuilder();
//            ub.where().eq("isAdded", true);
//            ub.updateColumnValue("isAdded", false);
//            ub.updateColumnValue("stockQuantity", 0);
//            ub.update();
//            Logger.d(TAG, "cleaned stock of my materials");
//        } catch (SQLException exc) {
//            Logger.e(exc);
//        }
    }


    public void updateExpense(Expense expense) throws SQLException {
        ExpenseRequest saveRequest = new ExpenseRequest();
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
    private List<Expense> getExpenses(String jobId, String searchQuery, @ExpenseUnit.Type String expenseType) throws SQLException {
        ExpenseRequest request = new ExpenseRequest().queryForListExpense(dataSource, jobId, searchQuery, expenseType);
        return dataSource.getList(request);
    }

    public List<Expense> getOtherExpenses(String jobId, String searchQuery) throws SQLException {
        return getExpenses(jobId, searchQuery, OTHER);
    }

    public List<Expense> getMaterialExpenses(String jobId) throws SQLException {
        return getExpenses(jobId, null, MATERIAL);
    }

    public long getTotalExpensesCount(String jobId) {
        try {
            long totalCount = countExpenses(jobId);

            ProjectRequest projectRequest = new ProjectRequest();
            projectRequest.queryForId(dataSource, jobId);
            Project project = dataSource.get(projectRequest);

            if (project != null) {
                List<Object> objects = getObjects(project.getObjectIds());
                for (Object o : objects) {
                    totalCount += getTotalExpensesCount(o.getId());
                }
            } else {

                OrderRequest orderRequest = new OrderRequest();
                orderRequest.queryForId(dataSource, jobId);
                Order order = dataSource.get(orderRequest);

                if (order != null) {
                    List<Object> objects = getObjects(order.getObjectIds());
                    for (Object o : objects) {
                        totalCount += getTotalExpensesCount(o.getId());
                    }
                }
            }
            return totalCount;
        } catch (SQLException e) {
            Logger.e(e);
            return 0;
        }
    }

    /**
     * @param jobId is field of Job object
     * @return total count of expenses for Job object with jobId field
     */
    public long countExpenses(String jobId) throws SQLException {
        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.queryCountForJobs(dataSource, jobId);
        return dataSource.count(expenseRequest);
    }

    public List<Expense> getAllExpenses(String jobId) {
        return getAllExpenses(jobId, null);
    }

    /**
     * Using for query expenses by date and jobId
     *
     * @param jobId is field of Job object
     * @param date  should be in "yyyy-MM-dd" format
     * @return list of expenses
     */
    public List<Expense> getAllExpenses(String jobId, String date) {
        List<Expense> allExpenses = new ArrayList<>();
        try {
            List<String> ids = new ArrayList<>();
            ids.add(jobId);

            ProjectRequest projectRequest = new ProjectRequest();
            projectRequest.queryForId(dataSource, jobId);
            Project project = dataSource.get(projectRequest);

            if (project != null) {
                Logger.d(TAG, "its a project, query should be also performed for all objects");
                ids.addAll(Arrays.asList(project.getObjectIds()));
            }

            OrderRequest orderRequest = new OrderRequest();
            orderRequest.queryForId(dataSource, jobId);
            Order order = dataSource.get(orderRequest);

            if (order != null) {
                Logger.d(TAG, "its an order, query should be also performed for all objects");
                ids.addAll(Arrays.asList(order.getObjectIds()));
            }

            ExpenseRequest expenseRequest = new ExpenseRequest();
            MaterialRequest materialRequest = new MaterialRequest();

            for (String id : ids) {

                expenseRequest.queryForListExpense(dataSource, jobId, date);
                List<Expense> foundExpense = dataSource.getList(expenseRequest);

                Logger.d(TAG, String.format("totally found %s expenses", foundExpense.size()));

                for (final Expense exp : foundExpense)
                    setValueWithUnit(exp, materialRequest);

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
    private void setValueWithUnit(@NonNull final Expense expense, final MaterialRequest materialRequest) {
        expense.setValueWithUnitName(new Expense.ExpenseValueWithUnit(expense.getValue()) {
            @Override
            public String getMaterialUnit() {
                try {
                    if (expense != null && materialRequest != null) {
                        String materialId = expense.getMaterialId();
                        Logger.d(TAG, String.format("material id for curr expense = %s", materialId));
                        materialRequest.queryForId(dataSource, materialId);
                        Material material = dataSource.get(materialRequest);
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
     * Save and retrieve last Expense from the table
     *
     * @param expense that will be saved
     * @return saved Expense
     */
    public Expense saveAndGetExpense(Expense expense) throws SQLException {
        ExpenseRequest saveRequest = new ExpenseRequest();
        saveRequest.setEntity(expense);
        dataSource.save(saveRequest);

        ExpenseRequest getRequest = new ExpenseRequest();
        getRequest.queryForLast(dataSource);
        return dataSource.get(getRequest);
    }

    public List<Object> getObjects(String[] ids) {
        List<Object> objects = new ArrayList<>();
        try {
            ObjectRequest objectRequest = new ObjectRequest();
            AddressRequest addressRequest = new AddressRequest();
            CustomerRequest customerRequest = new CustomerRequest();
            TypeRequest typeRequest = new TypeRequest();

            if (ids != null) {
                for (String id : ids) {
                    objectRequest.queryForId(dataSource, id);
                    Object o = dataSource.get(objectRequest);
                    if (o != null) {
                        addressRequest.queryForId(dataSource, o.getAddressId());
                        Address address = dataSource.get(addressRequest);
                        o.setAddress(address);

                        customerRequest.queryForId(dataSource, o.getCustomerId());
                        Customer customer = dataSource.get(customerRequest);
                        o.setCustomer(customer);

                        typeRequest.queryForId(dataSource, o.getStatusId());
                        Type status = dataSource.get(typeRequest);
                        o.setStatus(status);

                        objects.add(o);
                    }
                }
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return objects;
    }

    public void deleteExpense(Expense expense) {
        try {
            File file = getFile(expense);
            if (file != null) {
                java.io.File imageFile = file.getImageFile();
                boolean isDeleted = imageFile.delete();
                Logger.d("file deleted = " + isDeleted);
            }

            ExpenseRequest request = new ExpenseRequest();
            request.setEntity(expense);
            dataSource.delete(request);

        } catch (SQLException exc) {
            Logger.e(exc);
            exc.printStackTrace();
        }
    }

    public void saveAndGetExpense(String jobId, Material material, int countOfMaterials) throws SQLException {

        Expense expense = Expense.createMaterialExpense(jobId, material.getName(), material.getMaterialId(), countOfMaterials);
        material.setStockQuantity(material.getStockQuantity() - countOfMaterials);

        // TODO Should we count the price right here ???
        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setEntity(expense);
        dataSource.save(expenseRequest);

        MaterialRequest materialRequest = new MaterialRequest();
        materialRequest.setEntity(material);
        dataSource.update(materialRequest);
    }

    // region File
    public File getFile(Expense expense) throws SQLException {
        FileRequest fileRequest = new FileRequest();
        fileRequest.queryForFirst(dataSource, EXPENSE_ID);
        return dataSource.get(fileRequest);
    }

    public List<File> getFilesByExpenseId(String expenseId) throws SQLException {
        FileRequest fileRequest = new FileRequest();
        fileRequest.queryWhere(dataSource, FileEntity.ID, expenseId);
        return dataSource.getList(fileRequest);
    }

    public List<File> getFiles(Job job) throws SQLException {
        FileRequest fileRequest = new FileRequest();
        fileRequest.queryWhere(dataSource, JobEntity.ID, job.getId());
        return dataSource.getList(fileRequest);
    }

    public void saveFile(File file) throws SQLException {
        FileRequest fileRequest = new FileRequest();
        fileRequest.setEntity(file);
        dataSource.save(fileRequest);
    }

    public File saveFileAndGet(File file) throws SQLException {
        saveFile(file);
        // retrieve
        FileRequest fileRequest = new FileRequest();
        fileRequest.queryForLast(dataSource);
        return dataSource.get(fileRequest);
    }

    public void deleteFile(File file) throws SQLException {
        FileRequest fileRequest = new FileRequest();
        fileRequest.setEntity(file);
        dataSource.delete(fileRequest);
    }
    // endregion

    public User getUser(String userId) {
        try {
            UserRequest userRequest = new UserRequest();
            userRequest.queryForId(dataSource, userId);
            return dataSource.get(userRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }

    public User getRandomUser() {
        try {
            UserRequest userRequest = new UserRequest();
            userRequest.queryForId(dataSource, "0be618c9-e68b-435a-bdf4-d7f4ee6b6ba4");
            return dataSource.get(userRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }
}
