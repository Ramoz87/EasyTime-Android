package com.example.paralect.easytime.app;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

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

    public static List<Job> getJobs(@NonNull Context context) {
        return getJobs(context, null);
    }

    public static List<Job> getJobs(@NonNull Context context, Customer customer) {
        List<Job> jobs = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context.getApplicationContext());
        try {
            Dao<Object, String> objectDao = helper.getObjectDao();
            Dao<Order, String> orderDao = helper.getOrderDao();
            Dao<Project, String> projectDao = helper.getProjectDao();
            if (customer != null) {
                String customerId = customer.getCustomerId();
                jobs.addAll(objectDao.queryForEq("customerId", customerId));
                jobs.addAll(orderDao.queryForEq("customerId", customerId));
                jobs.addAll(projectDao.queryForEq("customerId", customerId));
            } else {
                jobs.addAll(objectDao.queryForAll());
                jobs.addAll(orderDao.queryForAll());
                jobs.addAll(projectDao.queryForAll());
                for (Job job : jobs) {
                    String customerId = job.getCustomerId();
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

    public static List<Customer> getCustomers(@NonNull Context context) {
        List<Customer> customers = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context.getApplicationContext());
        try {
            Dao<Customer, String> dao = helper.getCustomerDao();
            customers.addAll(dao.queryForAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
}
