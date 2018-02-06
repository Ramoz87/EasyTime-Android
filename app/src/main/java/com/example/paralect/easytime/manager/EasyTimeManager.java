package com.example.paralect.easytime.manager;

import android.text.TextUtils;
import android.util.Log;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Constants;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.DatabaseHelper;
import com.example.paralect.easytime.model.Expense;
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
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.TextUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.paralect.easytime.model.Type.TypeName.STATUS;
import static com.example.paralect.easytime.utils.CalendarUtils.SHORT_DATE_FORMAT;

/**
 * Created by alexei on 26.12.2017.
 */

public final class EasyTimeManager {
    private static final String TAG = EasyTimeManager.class.getSimpleName();

    private volatile static EasyTimeManager instance;
    private DatabaseHelper helper;

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
        if (helper == null)
            helper = new DatabaseHelper(EasyTimeApplication.getContext());
    }

    public DatabaseHelper getHelper() {
        return helper;
    }

    public void updateJob(Job job) {
        try {
            @ProjectType.Type int projectType = job.getProjectType();
            if (projectType == ProjectType.Type.TYPE_OBJECT) {
                helper.getObjectDao().update((Object) job);
            } else if (projectType == ProjectType.Type.TYPE_PROJECT) {
                helper.getProjectDao().update((Project) job);
            } else if (projectType == ProjectType.Type.TYPE_ORDER) {
                helper.getOrderDao().update((Order) job);
            }
        } catch (SQLException e) {
            Logger.e(TAG, e.getMessage());
        }
    }

    // region Type
    public Type getType(String typeId) {
        Type type = null;
        try {
            Dao<Type, String> dao = helper.getTypeDao();
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
            Dao<Type, String> dao = helper.getTypeDao();
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
            Dao<Contact, Long> dao = helper.getContactDao();
            return dao.queryForEq("customerId", customer.getCustomerId());
        } catch (SQLException exc) {
            Logger.e(exc);
            return new ArrayList<>();
        }
    }

    public Address getAddress(Customer customer) {
        try {
            Dao<Address, Long> dao = helper.getAddressDao();
            return dao.queryForId(customer.getAddressId());
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }

    public Type getStatus(Job job) {
        try {
            Dao<Type, String> dao = helper.getTypeDao();
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
            Dao<Object, String> objectDao = helper.getObjectDao();
            Dao<Order, String> orderDao = helper.getOrderDao();
            Dao<Project, String> projectDao = helper.getProjectDao();

            List<Object> objects = getList(objectDao, null, null, null);
            List<Order> orders = getList(orderDao, null, null, null);
            List<Project> projects = getList(projectDao, null, null, null);

            jobs.addAll(objects);
            jobs.addAll(orders);
            jobs.addAll(projects);

            Dao<Customer, String> customerDao = helper.getCustomerDao();
            for (Job job : jobs) {
                String customerId = job.getCustomerId();
                Customer customer = customerDao.queryForId(customerId);
                job.setCustomer(customer);
            }

            Dao<Address, Long> addressDao = helper.getAddressDao();
            for (Job job : jobs) {
                if (job instanceof JobWithAddress) {
                    JobWithAddress jobWithAddress = (JobWithAddress) job;
                    jobWithAddress.setAddress(addressDao.queryForId(jobWithAddress.getAddressId()));
                }
            }

            Dao<Type, String> typeDao = helper.getTypeDao();
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
        return getJobs(helper.getObjectDao(), customer, null, null);
    }

    public List<Order> getOrders(Customer customer) throws SQLException {
        return getJobs(helper.getOrderDao(), customer, null, null);
    }

    public List<Project> getProjects(Customer customer) throws SQLException {
        return getJobs(helper.getProjectDao(), customer, null, null);
    }

    public List<Integer> getJobTypes(Customer customer) {
        List<Integer> types = new ArrayList<>();
        try {
            String id = customer.getCustomerId();
            if (helper.getObjectDao().queryBuilder().where().eq("customerId", id).countOf() != 0)
                types.add(ProjectType.Type.TYPE_OBJECT);
            if (helper.getOrderDao().queryBuilder().where().eq("customerId", id).countOf() != 0)
                types.add(ProjectType.Type.TYPE_ORDER);
            if (helper.getProjectDao().queryBuilder().where().eq("customerId", id).countOf() != 0)
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
            if (projectType == ProjectType.Type.TYPE_OBJECT) dao = helper.getObjectDao();
            else if (projectType == ProjectType.Type.TYPE_PROJECT) dao = helper.getProjectDao();
            else if (projectType == ProjectType.Type.TYPE_ORDER) dao = helper.getOrderDao();
            else return 0L;
            return dao.queryBuilder().where().eq("customerId", customer.getCustomerId()).countOf();
        } catch (SQLException exc) {
            Logger.e(exc);
            return 0L;
        }
    }

    public <T extends Job> List<T> getJobs(Dao<T, String> dao, Customer customer, String query, String date) throws SQLException {

        String customerId = customer == null ? "" : customer.getCustomerId();
        List<T> jobs = getList(dao, customerId, query, date);

        if (customer == null) {
            for (Job job : jobs) {
                customerId = job.getCustomerId();
                Dao<Customer, String> customerDao = helper.getCustomerDao();
                customer = customerDao.queryForId(customerId);
                job.setCustomer(customer);
            }
        }

        Dao<Address, Long> addressDao = helper.getAddressDao();
        for (Job job : jobs) {
            if (job instanceof JobWithAddress) {
                JobWithAddress jobWithAddress = (JobWithAddress) job;
                jobWithAddress.setAddress(addressDao.queryForId(jobWithAddress.getAddressId()));
            }
        }

        Dao<Type, String> typeDao = helper.getTypeDao();
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
            Dao<Object, String> objectDao = helper.getObjectDao();
            Dao<Order, String> orderDao = helper.getOrderDao();
            Dao<Project, String> projectDao = helper.getProjectDao();

            String customerId = customer == null ? "" : customer.getCustomerId();

            List<Object> objects = getList(objectDao, customerId, query, date);
            List<Order> orders = getList(orderDao, customerId, query, date);
            List<Project> projects = getList(projectDao, customerId, query, date);

            jobs.addAll(objects);
            jobs.addAll(orders);
            jobs.addAll(projects);

            if (customer == null) {
                for (Job job : jobs) {
                    customerId = job.getCustomerId();
                    Dao<Customer, String> customerDao = helper.getCustomerDao();
                    customer = customerDao.queryForId(customerId);
                    job.setCustomer(customer);
                }
            }

            Dao<Address, Long> addressDao = helper.getAddressDao();
            for (Job job : jobs) {
                if (job instanceof JobWithAddress) {
                    JobWithAddress jobWithAddress = (JobWithAddress) job;
                    jobWithAddress.setAddress(addressDao.queryForId(jobWithAddress.getAddressId()));
                }
            }

            Dao<Type, String> typeDao = helper.getTypeDao();
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

            where.like("name", "%" + query + "%");
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
        Dao<User, String> dao = helper.getUserDao();
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
        Dao<Material, String> dao = helper.getMaterialDao();
        QueryBuilder<Material, String> qb = dao.queryBuilder();
        qb.where().like("name", "%" + query + "%");
        return qb.query();
    }

    public List<Customer> getCustomers(String query) {
        List<Customer> customers = new ArrayList<>();
        try {
            Dao<Customer, String> dao = helper.getCustomerDao();

            if (TextUtils.isEmpty(query))
                customers.addAll(dao.queryForAll());
            else {
                QueryBuilder<Customer, String> qb = dao.queryBuilder();
                qb.where().like("companyName", "%" + query + "%");
                customers = qb.query();
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return customers;
    }

    public void updateMaterial(Material material) {
        try {
            Dao<Material, String> dao = helper.getMaterialDao();
            dao.update(material);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
    }

    public List<Material> getMyMaterials() {
        List<Material> materials = new ArrayList<>();
        try {
            Dao<Material, String> dao = helper.getMaterialDao();
            QueryBuilder<Material, String> qb = dao.queryBuilder();
            // Where where = null;
            qb.where().like("isAdded", true);
            List<Material> myMaterials = qb.query();
            materials.addAll(myMaterials);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return materials;
    }

    public void deleteMyMaterials() {
        try {
            Dao<Material, String> dao = helper.getMaterialDao();
            UpdateBuilder<Material, String> ub = dao.updateBuilder();
            ub.where().eq("isAdded", true);
            ub.updateColumnValue("isAdded", false);
            ub.updateColumnValue("stockQuantity", 0);
            ub.update();
            Log.d(TAG, "cleaned stock of my materials");
        } catch (SQLException exc) {
            Logger.e(exc);
        }
    }

    public List<Expense> getDefaultExpenses(String jobId) {
        List<Expense> expenses = new ArrayList<>();

        // Driving
        Expense expense = new Expense();
        expense.setName(EasyTimeApplication.getContext().getString(R.string.driving));
        expense.setType(Expense.Type.DRIVING);
        expense.setJobId(jobId);
        expenses.add(expense);

        // Other expenses
        expense = new Expense();
        expense.setName(EasyTimeApplication.getContext().getString(R.string.other_expenses));
        expense.setType(Expense.Type.OTHER);
        expense.setJobId(jobId);
        expenses.add(expense);

        return expenses;
    }

    private List<Expense> getExpenses(String jobId, boolean isMaterial, String searchQuery, @Expense.Type String expenseType) {
        List<Expense> expenses = new ArrayList<>();
        try {
            Dao<Expense, Long> dao = helper.getExpenseDao();
            Dao<Material, String> materialDao = helper.getMaterialDao();
            QueryBuilder<Expense, Long> qb = dao.queryBuilder();

//            // Doesn't work in case of case sensitive
//            qb.distinct().selectColumns("name");

            Where where;
            if (isMaterial) {
                where = qb.where().eq("jobId", jobId).and().isNotNull("materialId");
            } else {
                where = qb.where().eq("jobId", jobId).and().isNull("materialId");
            }

            if (TextUtil.isNotEmpty(searchQuery)) {
                where.and().like("name", "%" + searchQuery + "%");
            }

            if (TextUtil.isNotEmpty(expenseType))
                where.and().eq("type", expenseType);

            List<Expense> foundExpenses = qb.query();

            if (isMaterial) {
                for (Expense exp : foundExpenses) {
                    Material material = materialDao.queryForId(exp.getMaterialId());
                    exp.setMaterial(material);
                    exp.getTypedValue();
                }
            } else{
                for (Expense exp : foundExpenses) {
                    exp.getTypedValue();
                }
            }
            expenses.addAll(foundExpenses);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return expenses;
    }

    public List<Expense> getOtherExpenses(String jobId, String searchQuery) {
        return getExpenses(jobId, false, searchQuery, Expense.Type.OTHER);
    }

    public List<Expense> getMaterialExpenses(String jobId) {
        return getExpenses(jobId, true, null, Expense.Type.MATERIAL);
    }

    public long getTotalExpensesCount(String jobId) {
        try {
            Dao<Expense, Long> dao = helper.getExpenseDao();
            Where where = dao.queryBuilder().where().eq("jobId", jobId);
            return where.countOf();
        } catch (SQLException e) {
            Logger.e(e);
            return 0;
        }
    }

    public List<Expense> getAllExpenses(String jobId) {
        return getAllExpenses(jobId, null);
    }

    public List<Expense> getAllExpenses(String jobId, String date) {
        List<Expense> allExpenses = new ArrayList<>();
        try {
            List<String> ids = new ArrayList<>();
            ids.add(jobId);
            Dao<Project, String> projectDao = helper.getProjectDao();
            Project project = projectDao.queryForId(jobId);
            if (project != null) {
                Log.d(TAG, "its a project, query should be also performed for all objects");
                ids.addAll(Arrays.asList(project.getObjectIds()));
            }
            Dao<Expense, Long> expenseDao = helper.getExpenseDao();
            Dao<Material, String> materialDao = helper.getMaterialDao();

            boolean hasDate = TextUtil.isNotEmpty(date);

            for (String id : ids) {
                QueryBuilder<Expense, Long> qb = expenseDao.queryBuilder();
                Where where = qb.where().eq("jobId", id);
                if (hasDate)
                    where.and().like("creationDate", "%" + date + "%");   // date should have "yyyy-MM-dd"
                List<Expense> foundExpense = qb.query();
                Log.d(TAG, String.format("totally found %s expenses", foundExpense.size()));

                for (Expense exp : foundExpense) {
                    if (exp.isMaterialExpense()) {
                        String materialId = exp.getMaterialId();
                        Log.d(TAG, String.format("material id for curr expense = %s", materialId));
                        Material material = materialDao.queryForId(materialId);
                        exp.setMaterial(material);
                    }
                    exp.getTypedValue();
                }

                allExpenses.addAll(foundExpense);
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return allExpenses;
    }

    public Expense saveExpense(Expense expense) throws SQLException {
        // save
        Dao<Expense, Long> dao = helper.getExpenseDao();
        dao.createOrUpdate(expense);
        // retrieve
        PreparedQuery<Expense> query = dao.queryBuilder()
                .orderBy("expenseId", false)
                .limit(1L)
                .prepare();

        return dao.query(query).get(0);
    }

    public List<Object> getObjects(String[] ids) {
        List<Object> objects = new ArrayList<>();
        try {
            Dao<Object, String> dao = helper.getObjectDao();
            if (ids != null) {
                for (String id : ids) {
                    Object o = dao.queryForId(id);
                    objects.add(o);
                }
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return objects;
    }

    public void deleteExpense(Expense expense) {
        try {
            Dao<Expense, Long> expenseDao = helper.getExpenseDao();
            File file = getFile(expense);
            if (file != null) {
                java.io.File imageFile = file.getImageFile();
                boolean isDeleted = imageFile.delete();
                Logger.d("file deleted = " + isDeleted);
            }
            expenseDao.delete(expense);
        } catch (SQLException exc) {
            Logger.e(exc);
            exc.printStackTrace();
        }
    }

    public void saveExpense(String jobId, Material material, int countOfMaterials) {
        try {
            Dao<Expense, Long> dao = helper.getExpenseDao();
            Dao<Material, String> materialDao = helper.getMaterialDao();
            Expense expense = Expense.createMaterialExpense(jobId, material, countOfMaterials);
            material.setStockQuantity(material.getStockQuantity() - countOfMaterials);
            // TODO Should we count the price right here ???
            dao.createOrUpdate(expense);
            materialDao.update(material);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
    }

    // region File
    public File getFile(Expense expense) throws SQLException {
        return helper.getFileDao().queryBuilder()
                .where()
                .eq("expenseId", expense.getExpenseId())
                .queryForFirst();
    }

    public List<File> getFilesByExpenseId(Long expenseId) throws SQLException {
        return helper.getFileDao().queryForEq("expenseId", expenseId);
    }

    public List<File> getFiles(Job job) throws SQLException {
        return helper.getFileDao().queryForEq("jobId", job.getJobId());
    }

    public void saveFile(File file) throws SQLException {
        helper.getFileDao().create(file);
    }

    public File saveFileAndGet(File file) throws SQLException {
        saveFile(file);
        // retrieve
        PreparedQuery<File> query = helper.getFileDao().queryBuilder()
                .orderBy("fileId", false)
                .limit(1L)
                .prepare();
        return helper.getFileDao().query(query).get(0);
    }

    public void deleteFile(File file) throws SQLException {
        helper.getFileDao().delete(file);
    }
    // endregion

    public User getUser(String userId) {
        try {
            Dao<User, String> dao = helper.getUserDao();
            return dao.queryForId(userId);
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }

    public User getRandomUser() {
        try {
            Dao<User, String> dao = helper.getUserDao();
            return dao.queryForId("0be618c9-e68b-435a-bdf4-d7f4ee6b6ba4");
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }
}
