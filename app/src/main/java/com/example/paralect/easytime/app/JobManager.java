package com.example.paralect.easytime.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.DatabaseHelper;
import com.example.paralect.easytime.model.Job;
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

public final class JobManager {

    public static List<Job> loadFromAssets(@NonNull Context context) {
        return loadFromAssets(context, null);
    }

    public static List<Job> loadFromAssets(@NonNull Context context, Customer customer) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jobs;
    }
}
