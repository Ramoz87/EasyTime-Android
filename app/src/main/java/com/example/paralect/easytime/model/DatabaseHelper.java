package com.example.paralect.easytime.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.*;
import java.sql.SQLException;

/**
 * Created by alexei on 03.01.2018.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "easy_time_db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Address, Long> addressDao = null;
    private Dao<User, String> userDao = null;
    private Dao<Type, String> typeDao = null;
    private Dao<Customer, String> customerDao = null;
    private Dao<Material, String> materialDao = null;
    private Dao<Object, String> objectDao = null;
    private Dao<Order, String> orderDao = null;
    private Dao<Project, String> projectDao = null;
    private Dao<File, String> fileDao = null;

    public DatabaseHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Address.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, Type.class);
            TableUtils.createTableIfNotExists(connectionSource, Customer.class);
            TableUtils.createTableIfNotExists(connectionSource, Material.class);
            TableUtils.createTableIfNotExists(connectionSource, Object.class);
            TableUtils.createTableIfNotExists(connectionSource, Order.class);
            TableUtils.createTableIfNotExists(connectionSource, Project.class);
            TableUtils.createTableIfNotExists(connectionSource, File.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Address.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Type.class, true);
            TableUtils.dropTable(connectionSource, Customer.class, true);
            TableUtils.dropTable(connectionSource, Material.class, true);
            TableUtils.dropTable(connectionSource, Object.class, true);
            TableUtils.dropTable(connectionSource, Order.class, true);
            TableUtils.dropTable(connectionSource, Project.class, true);
            TableUtils.dropTable(connectionSource, File.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Address, Long> getAddressDao() throws SQLException {
        if (addressDao == null) {
            addressDao = getDao(Address.class);
        }
        return addressDao;
    }

    public Dao<User, String> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public Dao<Type, String> getTypeDao() throws SQLException {
        if (typeDao == null) {
            typeDao = getDao(Type.class);
        }
        return typeDao;
    }

    @Override
    public void close() {
        addressDao = null;
        userDao = null;
        customerDao = null;
        typeDao = null;
        materialDao = null;
        orderDao = null;
        objectDao = null;
        projectDao = null;
        fileDao = null;

        super.close();
    }

    public Dao<Customer, String> getCustomerDao() throws SQLException {
        if (customerDao == null) {
            customerDao = getDao(Customer.class);
        }
        return customerDao;
    }

    public Dao<Material, String> getMaterialDao() throws SQLException {
        if (materialDao == null) {
            materialDao = getDao(Material.class);
        }
        return materialDao;
    }

    public Dao<Object, String> getObjectDao() throws SQLException {
        if (objectDao == null) {
            objectDao = getDao(Object.class);
        }
        return objectDao;
    }

    public Dao<Order, String> getOrderDao() throws SQLException {
        if (orderDao == null) {
            orderDao = getDao(Order.class);
        }
        return orderDao;
    }

    public Dao<Project, String> getProjectDao() throws SQLException {
        if (projectDao == null) {
            projectDao = getDao(Project.class);
        }
        return projectDao;
    }

    public Dao<File, String> getFileDao() throws SQLException {
        if (fileDao == null) {
            fileDao = getDao(File.class);
        }
        return fileDao;
    }
}
