package com.example.paralect.easytime.manager;

import android.text.TextUtils;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Consumable;
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
import com.example.paralect.easytime.utils.CollectionUtils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

public final class EasyTimeManager {

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
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
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
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
        return jobs;
    }

    private static <JOB> List<JOB> getList(Dao<JOB, String> dao, String customerId, String query, String date) throws SQLException {
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
            if (where == null) where = qb.where();
            else where.and();

            where.eq("date", date);
        }

        List<JOB> objects = qb.query();
        return objects;
    }
    // endregion

    public List<Material> getMaterials() {
        List<Material> materials = new ArrayList<>();
        try {
            Dao<Material, String> dao = helper.getMaterialDao();
            materials.addAll(dao.queryForAll());
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
        return materials;
        // return getMaterials(null);
    }

    public List<Material> getMaterials(String query) {
        List<Material> materials = new ArrayList<>();
        try {
            Dao<Material, String> dao = helper.getMaterialDao();
            QueryBuilder<Material, String> qb = dao.queryBuilder();
            // Where where = null;
            if (query != null && !query.isEmpty()) {
                qb.where().like("name", "%" + query + "%");
            }

            materials.addAll(qb.query());
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
        return materials;
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
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
        return customers;
    }

    public void updateMaterial(Material material) {
        try {
            Dao<Material, String> dao = helper.getMaterialDao();
            dao.update(material);
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
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
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
        return materials;
    }

    public List<Expense> getDefaultExpenses(Job job) {
        List<Expense> expenses = new ArrayList<>();
        Expense expense = new Expense();
        expense.setName(EasyTimeApplication.getContext().getString(R.string.driving));
        expense.setJobId(job.getJobId());
        expenses.add(expense);
        return expenses;
    }

    public List<Expense> getExpenses(String jobId) {
        List<Expense> expenses = new ArrayList<>();
        try {
            Dao<Expense, Long> dao = helper.getExpenseDao();
            List<Expense> foundExpenses = dao.queryForEq("jobId", jobId);
            expenses.addAll(foundExpenses);
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
        return expenses;
    }

    public List<Expense> getExpenses(Job job) {
        return getExpenses(job.getJobId());
    }

    public void saveOrUpdateExpense(Expense expense) {
        try {
            Dao<Expense, Long> dao = helper.getExpenseDao();
            dao.createOrUpdate(expense);
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    public Expense saveExpense(Expense expense) throws SQLException {
        // save
        Dao<Expense, Long> dao = helper.getExpenseDao();
        dao.createOrUpdate(expense);
        // retrieve
        PreparedQuery<Expense> query = dao.queryBuilder()
                .orderBy("expensiveId", false)
                .limit(1L)
                .prepare();

        return dao.query(query).get(0);
    }

    public void updateExpense(Expense expense) {
        try {
            Dao<Expense, Long> dao = helper.getExpenseDao();
            dao.update(expense);
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    public List<Object> getObjects(Project project) {
        List<Object> objects = new ArrayList<>();
        try {
            Dao<Object, String> dao = helper.getObjectDao();
            String[] ids = project.getObjectIds();
            if (ids != null) {
                for (String id : ids) {
                    Object o = dao.queryForId(id);
                    objects.add(o);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objects;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objects;
    }

    public List<Consumable> getConsumables(String jobId) {
        List<Consumable> consumables = new ArrayList<>();
        try {
            Dao<Expense, Long> dao = helper.getExpenseDao();
            List<Expense> expenses = dao.queryForEq("jobId", jobId);
            Dao<Material, String> materialDao = helper.getMaterialDao();
            for (Expense e : expenses) {
                String materialId = e.getMaterialId();
                List<File> files = getFilesByExpenseId(e.getExpensiveId());
                if (materialId != null) {
                    Material m = materialDao.queryForId(materialId);
                    Material newMaterial = new Material(m);
                    newMaterial.setStockQuantity(e.getValue() / m.getPricePerUnit());
                    consumables.add(newMaterial);
                } else {
                    consumables.add(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consumables;
    }

    public void deleteConsumable(Consumable consumable) {
        // delete expense
    }

    public void saveExpense(String jobId, Material material, int value) {
        try {
            Dao<Expense, Long> dao = helper.getExpenseDao();
            Where<Expense, Long> where = dao.queryBuilder().where();
            where.eq("jobId", jobId).and().eq("materialId", material.getMaterialId());
            List<Expense> foundExpenses = where.query();
            Expense expense;
            if (CollectionUtils.isEmpty(foundExpenses)) {
                expense = new Expense();
                expense.setJobId(jobId);
                expense.setName(material.getName());
                expense.setMaterialId(material.getMaterialId());
                expense.setValue(value);
            } else {
                expense = foundExpenses.get(0);
                expense.setValue(value);
            }
            dao.createOrUpdate(expense);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // region File
    public File getFile(Expense expense) throws SQLException {
        return helper.getFileDao().queryBuilder()
                .where()
                .eq("expensiveId", expense.getExpensiveId())
                .queryForFirst();
    }

    public List<File> getFilesByExpenseId(Long expenseId) throws SQLException {
        return helper.getFileDao().queryForEq("expensiveId", expenseId);
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

}
