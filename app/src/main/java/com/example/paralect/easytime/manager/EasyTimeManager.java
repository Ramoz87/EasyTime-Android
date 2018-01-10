package com.example.paralect.easytime.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

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

    private Context context;
    private DatabaseHelper databaseHelper;

    EasyTimeManager(@NonNull Context applicationContext) {
        this.context = applicationContext;
    }

    private void initDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
    }

    public static List<Job> getJobs(@NonNull Context context, Customer customer, String query, String date) {
        List<Job> jobs = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context.getApplicationContext());
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

//        if (hasCustomerId && hasQuery && hasDate)
//            qb.where().eq("customerId", customerId)
//                    .and().like("name", "%" + query + "%")
//                    .and().eq("date", date);
//        else if (hasCustomerId)
//            qb.where().eq("customerId", customerId);
//        else if (hasQuery)
//            qb.where().like("name", "%" + query + "%");

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


    public static List<Material> getMaterials(@NonNull Context context) {
        List<Material> materials = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context.getApplicationContext());
        try {
            Dao<Material, String> dao = helper.getMaterialDao();
            materials.addAll(dao.queryForAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return materials;
    }

    public static List<Customer> getCustomers(@NonNull Context context, String query) {
        List<Customer> customers = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context.getApplicationContext());
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
