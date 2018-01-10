package com.example.paralect.easytime;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.paralect.easytime.manager.ETPreferenceManager;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.utils.FakeCreator;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.DatabaseHelper;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.JobWithAddress;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.model.User;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

public class EasyTimeApplication extends Application {
    private static final String TAG = EasyTimeApplication.class.getSimpleName();

    @SuppressLint("StaticFieldLeak") private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        ETPreferenceManager preferenceManager = ETPreferenceManager.getInstance(this);
        preferenceManager.plusLaunch();
        if (BuildConfig.DEBUG && preferenceManager.isLaunchFirst()) { // pre-populate data from assets
            Log.d(TAG, "filling data from db");
            FakeCreator fakeCreator = getDefaultFakeCreator();
            fillData(fakeCreator);
        }
    }

    public static Context getContext() {
        return sContext;
    }

    private void fillData(FakeCreator fakeCreator) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        final String userCSVPath = "db/users.csv";
        final String typeCSVPath = "db/types.csv";
        final String customerCSVPath = "db/customers.csv";
        final String materialCSVPath = "db/materials.csv";
        final String objectCSVPath = "db/objects.csv";
        final String orderCSVPath = "db/orders.csv";
        final String projectCSVPath = "db/projects.csv";

        fillData(fakeCreator, userCSVPath, User.class, databaseHelper);
        fillData(fakeCreator, typeCSVPath, Type.class, databaseHelper);
        fillData(fakeCreator, customerCSVPath, Customer.class, databaseHelper);
        fillData(fakeCreator, materialCSVPath, Material.class, databaseHelper);
        fillData(fakeCreator, objectCSVPath, Object.class, databaseHelper);
        fillData(fakeCreator, orderCSVPath, Order.class, databaseHelper);
        fillData(fakeCreator, projectCSVPath, Project.class, databaseHelper);
    }

    private <E> void fillData(FakeCreator fakeCreator, String csvPath, Class<E> clazz, DatabaseHelper databaseHelper) {
        try {
            List<E> items = fakeCreator.parse(csvPath, clazz);
            Dao<E, String> dao = databaseHelper.getDao(clazz);
            Log.d(TAG, String.format("===// %s //===", clazz.getSimpleName()));

            Dao<Address, Long> addressDao = databaseHelper.getAddressDao();
            for (E item : items) {
                Log.d(TAG, item.toString());

                if (item instanceof JobWithAddress) {
                    JobWithAddress job = (JobWithAddress) item;
                    Address address = job.getAddress();
                    addressDao.create(address);
                    job.setAddressId(address.getAddressId());
                }

                dao.createOrUpdate(item);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private FakeCreator getDefaultFakeCreator() {
        return new FakeCreator(getAssets()) {
            @Override
            public <E> java.lang.Object create(Class<E> clazz, String[] fields) {
                if (clazz.equals(User.class)) {
                    return createUser(fields);
                } else if (clazz.equals(Customer.class)) {
                    return createCustomer(fields);
                } else if (clazz.equals(Type.class)) {
                    return createType(fields);
                } else if (clazz.equals(Material.class)) {
                    return createMaterial(fields);
                } else if (clazz.equals(Object.class)) {
                    return createObject(fields);
                } else if (clazz.equals(Order.class)) {
                    return createOrder(fields);
                } else if (clazz.equals(Project.class)) {
                    return createProject(fields);
                }
                return null;
            }

            User createUser(String[] fields) {
                User user = new User();
                user.setUserId(fields[0]);
                user.setFirstName(fields[1]);
                user.setLastName(fields[2]);
                user.setUserName(fields[3]);
                user.setPassword(fields[4]);
                return user;
            }

            Type createType(String[] fields) {
                Type type = new Type();
                type.setTypeId(fields[0]);
                type.setName(fields[1]);
                type.setType(fields[2]);
                return type;
            }

            Customer createCustomer(String[] fields) {
                Customer customer = new Customer();
                customer.setCustomerId(fields[21]);
                customer.setCompanyName(fields[38]);
                customer.setFirstName(fields[53]);
                customer.setLastName(fields[38]);
                return customer;
            }

            Material createMaterial(String[] fields) {
                Material material = new Material();
                material.setMaterialId(fields[0]);
                material.setCurrency(fields[1]);
                material.setMaterialNr(Integer.valueOf(fields[2]));
                material.setName(fields[3]);
                material.setPricePerUnit(Integer.valueOf(fields[4]));
                material.setSerialNr(Long.valueOf(fields[5]));
                material.setUnitId(fields[6]);
                return material;
            }

            Object createObject(String[] fields) {
                Object object = new Object();
                fillJob(object, fields);

                Address address = new Address();
                address.setStreet(fields[16]);
                address.setCity(fields[17]);
                address.setZip(fields[18]);
                object.setAddress(address);
                return object;
            }

            Order createOrder(String[] fields) {
                Order order = new Order();
                fillJob(order, fields);

                order.setContact(fields[13]);
                order.setDeliveryTime(fields[14]);

                Address address = new Address();
                address.setStreet(fields[15]);
                address.setCity(fields[16]);
                address.setZip(fields[17]);
                order.setAddress(address);
                return order;
            }

            Project createProject(String[] fields) {
                Project project = new Project();
                fillJob(project, fields);

                project.setDateStart(fields[10]);
                project.setDateEnd(fields[11]);

                List<String> objectIds = new ArrayList<>();
                objectIds.add(fields[13]);
                project.setObjectIds(objectIds);
                return project;
            }

            void fillJob(Job job, String[] fields) {
                job.setEntityType(fields[0]);
                job.setJobId(fields[1]);
                job.setCustomerId(fields[2]);
                job.setStatusId(fields[3]);
                job.setTypeId(fields[4]);
                job.setNumber(Integer.valueOf(fields[5]));
                job.setName(fields[6]);
                job.setInformation(fields[7]);
                // fields[8]?
                job.setCurrency(fields[9]);

                // random date
                Date date = CalendarUtils.nextDate();
                String dateString = CalendarUtils.stringFromDate(date, CalendarUtils.DEFAULT_DATE_FORMAT);
                job.setDate(dateString);
            }
        };
    }
}
