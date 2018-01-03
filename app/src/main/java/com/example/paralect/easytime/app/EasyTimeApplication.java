package com.example.paralect.easytime.app;

import android.app.Application;
import android.util.Log;

import com.example.paralect.easytime.BuildConfig;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.DatabaseHelper;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.model.User;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

public class EasyTimeApplication extends Application {
    private static final String TAG = EasyTimeApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) { // pre-populate data from assets
            FakeCreator fakeCreator = getDefaultFakeCreator();
            fillData(fakeCreator);
        }
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
            for (E item : items) {
                Log.d(TAG, item.toString());
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
                object.setEntityType(fields[0]);
                object.setJobId(fields[1]);
                object.setCustomerId(fields[2]);
                object.setStatusId(fields[3]);
                object.setTypeId(fields[4]);
                object.setNumber(Integer.valueOf(fields[5]));
                object.setName(fields[6]);
                object.setInformation(fields[7]);

                object.setCurrency(fields[9]);
                return object;
            }

            Order createOrder(String[] fields) {
                Order order = new Order();
                order.setEntityType(fields[0]);
                order.setJobId(fields[1]);
                order.setCustomerId(fields[2]);
                order.setStatusId(fields[3]);
                order.setTypeId(fields[4]);
                order.setNumber(Integer.valueOf(fields[5]));
                order.setName(fields[6]);
                order.setInformation(fields[7]);

                order.setCurrency(fields[9]);

                order.setContact(fields[13]);
                order.setDeliveryTime(fields[14]);

                Address address = new Address();
                address.setStreet(fields[15]);
                address.setCity(fields[16]);
                address.setZip(fields[17]);
                order.setDeliveryAddress(address);
                return order;
            }

            Project createProject(String[] fields) {
                Project project = new Project();
                project.setEntityType(fields[0]);
                project.setJobId(fields[1]);
                project.setCustomerId(fields[2]);
                project.setStatusId(fields[3]);
                project.setTypeId(fields[4]);
                project.setNumber(Integer.valueOf(fields[5]));
                project.setName(fields[6]);
                project.setInformation(fields[7]);

                project.setCurrency(fields[9]);

                return project;
            }
        };
    }
}
