package com.example.paralect.easytime.manager;

import android.text.TextUtils;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.DatabaseHelper;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.JobWithAddress;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.j256.ormlite.dao.Dao;
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
        return customers;
    }

}
