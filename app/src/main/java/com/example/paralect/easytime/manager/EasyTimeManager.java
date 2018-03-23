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
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.utils.CollectionUtil;
import com.example.paralect.easytime.utils.ExpenseUtil;
import com.example.paralect.easytime.utils.Logger;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.paralect.easytimedataormlite.DatabaseHelperORMLite;
import com.paralect.easytimedataormlite.request.ExpenseRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.paralect.easytime.model.ExpenseUnit.Type.MATERIAL;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.OTHER;
import static com.example.paralect.easytime.model.Type.TypeName.STATUS;
import static com.example.paralect.easytime.utils.CalendarUtils.SHORT_DATE_FORMAT;

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
        try {
            @ProjectType.Type int projectType = job.getProjectType();
            if (projectType == ProjectType.Type.TYPE_OBJECT) {
                dataSource.update(Object.class, (Object)job);
            } else if (projectType == ProjectType.Type.TYPE_PROJECT) {
                dataSource.update(Project.class, (Project)job);
            } else if (projectType == ProjectType.Type.TYPE_ORDER) {
                dataSource.update(Order.class, (Order)job);
            }
        } catch (SQLException e) {
            Logger.e(TAG, e.getMessage());
        }
    }

    // region TypeEntity
    public Type getType(String typeId) {
        Type type = null;
        try {
            Dao<Type, String> dao = dataSource.getTypeDao();
            type = dao.queryForId(typeId);
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
            Dao<Type, String> dao = dataSource.getTypeDao();
            if (!TextUtils.isEmpty(type)) {
                return dao.query(dao.queryBuilder()
                        .where()
                        .eq("type", type)
                        .and()
                        .like("name", "%" + searchName + "%")
                        .prepare());
            } else {
                return dao.queryForAll();
            }
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }
    // endregion

    public List<Contact> getContacts(Customer customer) {
        try {
            Dao<Contact, Long> dao = dataSource.getContactDao();
            return dao.queryForEq("customerId", customer.getId());
        } catch (SQLException exc) {
            Logger.e(exc);
            return new ArrayList<>();
        }
    }

    public Address getAddress(Customer customer) {
        try {
            Dao<Address, Long> dao = dataSource.getAddressDao();
            return dao.queryForId(customer.getAddressId());
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }

    public Type getStatus(Job job) {
        try {
            Dao<Type, String> dao = dataSource.getTypeDao();
            List<Type> results = dao.queryBuilder().where().idEq(job.getStatusId()).query();
            if (!CollectionUtil.isEmpty(results)) return results.get(0);
            else return null;
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }

    // region Jobs
    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        try {
            Dao<Object, String> objectDao = dataSource.getObjectDao();
            Dao<Order, String> orderDao = dataSource.getOrderDao();
            Dao<Project, String> projectDao = dataSource.getProjectDao();

            List<Object> objects = getList(objectDao, null, null, null);
            List<Order> orders = getList(orderDao, null, null, null);
            List<Project> projects = getList(projectDao, null, null, null);

            jobs.addAll(objects);
            jobs.addAll(orders);
            jobs.addAll(projects);

            Dao<Customer, String> customerDao = dataSource.getCustomerDao();
            for (Job job : jobs) {
                String customerId = job.getCustomerId();
                Customer customer = customerDao.queryForId(customerId);
                job.setCustomer(customer);
            }

            Dao<Address, Long> addressDao = dataSource.getAddressDao();
            for (Job job : jobs) {
                if (job instanceof JobWithAddress) {
                    JobWithAddress jobWithAddress = (JobWithAddress) job;
                    jobWithAddress.setAddress(addressDao.queryForId(jobWithAddress.getAddressId()));
                }
            }

            Dao<Type, String> typeDao = dataSource.getTypeDao();
            for (Job job : jobs) {
                String statusId = job.getStatusId();
                Type status = typeDao.queryForId(statusId);
                job.setStatus(status);
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return jobs;
    }

    public List<Object> getObjects(Customer customer) throws SQLException {
        return getJobs(dataSource.getObjectDao(), customer, null, null);
    }

    public List<Order> getOrders(Customer customer) throws SQLException {
        return getJobs(dataSource.getOrderDao(), customer, null, null);
    }

    public List<Project> getProjects(Customer customer) throws SQLException {
        return getJobs(dataSource.getProjectDao(), customer, null, null);
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

        String customerId = customer == null ? "" : customer.getId();
        List<T> jobs = getList(dao, customerId, query, date);

        if (customer == null) {
            for (Job job : jobs) {
                customerId = job.getCustomerId();
                Dao<Customer, String> customerDao = dataSource.getCustomerDao();
                customer = customerDao.queryForId(customerId);
                job.setCustomer(customer);
            }
        }

        Dao<Address, Long> addressDao = dataSource.getAddressDao();
        for (Job job : jobs) {
            if (job instanceof JobWithAddress) {
                JobWithAddress jobWithAddress = (JobWithAddress) job;
                jobWithAddress.setAddress(addressDao.queryForId(jobWithAddress.getAddressId()));
            }
        }

        Dao<Type, String> typeDao = dataSource.getTypeDao();
        for (Job job : jobs) {
            String statusId = job.getStatusId();
            Type status = typeDao.queryForId(statusId);
            job.setStatus(status);
        }
        return jobs;
    }

    public List<Job> getJobs(Customer customer, String query, String date) {
        List<Job> jobs = new ArrayList<>();
        try {
            Dao<Object, String> objectDao = dataSource.getObjectDao();
            Dao<Order, String> orderDao = dataSource.getOrderDao();
            Dao<Project, String> projectDao = dataSource.getProjectDao();

            String customerId = customer == null ? "" : customer.getId();

            List<Object> objects = getList(objectDao, customerId, query, date);
            List<Order> orders = getList(orderDao, customerId, query, date);
            List<Project> projects = getList(projectDao, customerId, query, date);

            jobs.addAll(objects);
            jobs.addAll(orders);
            jobs.addAll(projects);

            if (customer == null) {
                for (Job job : jobs) {
                    customerId = job.getCustomerId();
                    Dao<Customer, String> customerDao = dataSource.getCustomerDao();
                    customer = customerDao.queryForId(customerId);
                    job.setCustomer(customer);
                }
            }

            Dao<Address, Long> addressDao = dataSource.getAddressDao();
            for (Job job : jobs) {
                if (job instanceof JobWithAddress) {
                    JobWithAddress jobWithAddress = (JobWithAddress) job;
                    jobWithAddress.setAddress(addressDao.queryForId(jobWithAddress.getAddressId()));
                }
            }

            Dao<Type, String> typeDao = dataSource.getTypeDao();
            for (Job job : jobs) {
                String statusId = job.getStatusId();
                Type status = typeDao.queryForId(statusId);
                job.setStatus(status);
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return jobs;
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
        String[] ids = job.getMemberIds();
        if (ids == null || ids.length == 0) return null;
        List<User> users = new ArrayList<>();
        Dao<User, String> dao = dataSource.getUserDao();
        if (ids != null) {
            for (String id : ids) {
                User user = dao.queryForId(id);
                users.add(user);
            }
        }
        return users;
    }
    // endregion

    public List<Material> getMaterials(String query) throws SQLException {
        Dao<Material, String> dao = dataSource.getMaterialDao();
        QueryBuilder<Material, String> qb = dao.queryBuilder();
        qb.where().like("name", "%" + query + "%");
        return qb.query();
    }

    public List<Customer> getCustomers(String query) {
        List<Customer> customers = new ArrayList<>();
        try {
            Dao<Customer, String> dao = dataSource.getCustomerDao();

            if (TextUtils.isEmpty(query))
                customers.addAll(dao.queryForAll());
            else {
                QueryBuilder<Customer, String> qb = dao.queryBuilder();
                qb.where().like("companyName", "%" + query + "%");
                customers = dataSource.getList(Customer.class, qb);
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return customers;
    }

    public void updateMaterial(Material material) {
        try {
            dataSource.update(Material.class, material);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
    }

    public List<Material> getMyMaterials() {
        List<Material> materials = new ArrayList<>();
        try {
            Dao<Material, String> dao = dataSource.getMaterialDao();
            QueryBuilder<Material, String> qb = dao.queryBuilder();
            // Where where = null;
            qb.where().like("isAdded", true);
            List<Material> myMaterials = qb
                    .orderByRaw("stockQuantity IS 0 ASC")
                    .orderBy("name", true)
                    .query();

            materials.addAll(myMaterials);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return materials;
    }

    public void deleteMyMaterials() {
        try {
            Dao<Material, String> dao = dataSource.getMaterialDao();
            UpdateBuilder<Material, String> ub = dao.updateBuilder();
            ub.where().eq("isAdded", true);
            ub.updateColumnValue("isAdded", false);
            ub.updateColumnValue("stockQuantity", 0);
            ub.update();
            Logger.d(TAG, "cleaned stock of my materials");
        } catch (SQLException exc) {
            Logger.e(exc);
        }
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

        QueryBuilder<Expense, Long> qb = dataSource.getExpenseDao().queryBuilder();


        List<Expense> expenses = dataSource.getList(Expense.class, qb);

        ExpenseRequest request = new ExpenseRequest();

        Expense ex = dataSource.get(request);

        return dataSource.get(Expense.class, query);

        for (final Expense exp : expenses)
            setValueWithUnit(exp, materialDao);

        return expenses;
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

            Dao<Project, String> projectDao = dataSource.getProjectDao();
            Project project = projectDao.queryForId(jobId);
            if (project != null) {
                List<Object> objects = getObjects(project.getObjectIds());
                for (Object o : objects) {
                    totalCount += getTotalExpensesCount(o.getId());
                }
            } else {
                Dao<Order, String> orderDao = dataSource.getOrderDao();
                Order order = orderDao.queryForId(jobId);
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
        List<Expense> allExpenses = new ArrayList<>();
        try {
            List<String> ids = new ArrayList<>();
            ids.add(jobId);
            Dao<Project, String> projectDao = dataSource.getProjectDao();
            Project project = projectDao.queryForId(jobId);
            if (project != null) {
                Logger.d(TAG, "its a project, query should be also performed for all objects");
                ids.addAll(Arrays.asList(project.getObjectIds()));
            }
            Dao<Order, String> orderDao = dataSource.getOrderDao();
            Order order = orderDao.queryForId(jobId);
            if (order != null) {
                Logger.d(TAG, "its an order, query should be also performed for all objects");
                ids.addAll(Arrays.asList(order.getObjectIds()));
            }
            final Dao<Material, String> materialDao = dataSource.getMaterialDao();

            for (String id : ids) {

                List<Expense> foundExpense = getExpenses(id, date);
                Logger.d(TAG, String.format("totally found %s expenses", foundExpense.size()));

                for (final Expense exp : foundExpense)
                    setValueWithUnit(exp, materialDao);

                allExpenses.addAll(foundExpense);
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return allExpenses;
    }

    public List<Expense> getExpenses(String jobId, String date) throws SQLException {
        boolean hasDate = !TextUtils.isEmpty(date);
        QueryBuilder<Expense, Long> qb = dataSource.getExpenseDao().queryBuilder();
        Where where = qb.where().eq(JOB_ID, jobId);
        if (hasDate) {

            Date time = ExpenseUtil.dateFromString(date, ExpenseUtil.SHORT_DATE_FORMAT);

            Calendar yesterday = Calendar.getInstance();
            yesterday.setTime(time);

            Calendar tomorrow = Calendar.getInstance();
            tomorrow.setTime(time);
            tomorrow.add(Calendar.DATE, 1);

            long beforeTime = yesterday.getTimeInMillis();
            long afterTime = tomorrow.getTimeInMillis();
            where.and().between(CREATION_DATE, beforeTime, afterTime);

        }
        qb.orderBy(CREATION_DATE, false);
        return dataSource.getList(Expense.class, qb);
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
                com.example.paralect.easytime.model.Type t =
                        EasyTimeManager.getInstance().getType(material.getUnitId());
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
        QueryBuilder<Expense, Long> query = dataSource.getExpenseDao().queryBuilder()
                .orderBy(EXPENSE_ID, false)
                .limit(1L);
        dataSource.save(Expense.class, expense);

        ExpenseRequest converter = new ExpenseRequest();
        Expense ex = dataSource.get(converter);

        return dataSource.get(Expense.class, query);
    }

    public List<Object> getObjects(String[] ids) {
        List<Object> objects = new ArrayList<>();
        try {
            Dao<Object, String> dao = dataSource.getObjectDao();
            Dao<Customer, String> customerDao = dataSource.getCustomerDao();
            Dao<Address, Long> addressDao = dataSource.getAddressDao();
            Dao<Type, String> typeDao = dataSource.getTypeDao();
            if (ids != null) {
                for (String id : ids) {
                    Object o = dao.queryForId(id);
                    if (o != null) {
                        Address address = addressDao.queryForId(o.getAddressId());
                        o.setAddress(address);

                        Customer customer = customerDao.queryForId(o.getCustomerId());
                        o.setCustomer(customer);

                        Type status = typeDao.queryForId(o.getStatusId());
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
            dataSource.delete(Expense.class, expense);
        } catch (SQLException exc) {
            Logger.e(exc);
            exc.printStackTrace();
        }
    }

    public void saveAndGetExpense(String jobId, Material material, int countOfMaterials) throws SQLException {
        Dao<Material, String> materialDao = dataSource.getMaterialDao();
        Expense expense = Expense.createMaterialExpense(jobId, material.getName(), material.getId(), countOfMaterials);
        material.setStockQuantity(material.getStockQuantity() - countOfMaterials);
        // TODO Should we count the price right here ???
        dataSource.save(Expense.class, expense);
        dataSource.update(Material.class, material);
    }

    // region FileEntity
    public File getFile(Expense expense) throws SQLException {
        return dataSource.getFileDao().queryBuilder()
                .where()
                .eq(EXPENSE_ID, expense.getId())
                .queryForFirst();
    }

    public List<File> getFilesByExpenseId(Long expenseId) throws SQLException {
        return dataSource.getFileDao().queryForEq(EXPENSE_ID, expenseId);
    }

    public List<File> getFiles(Job job) throws SQLException {
        return dataSource.getFileDao().queryForEq("jobId", job.getId());
    }

    public void saveFile(File file) throws SQLException {
        dataSource.getFileDao().create(file);
    }

    public File saveFileAndGet(File file) throws SQLException {
        saveFile(file);
        // retrieve
        PreparedQuery<File> query = dataSource.getFileDao().queryBuilder()
                .orderBy("fileId", false)
                .limit(1L)
                .prepare();
        return dataSource.getFileDao().query(query).get(0);
    }

    public void deleteFile(File file) throws SQLException {
        dataSource.delete(File.class, file);
    }
    // endregion

    public User getUser(String userId) {
        try {
            Dao<User, String> dao = dataSource.getUserDao();
            return dao.queryForId(userId);
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }

    public User getRandomUser() {
        try {
            Dao<User, String> dao = dataSource.getUserDao();
            return dao.queryForId("0be618c9-e68b-435a-bdf4-d7f4ee6b6ba4");
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }
}
