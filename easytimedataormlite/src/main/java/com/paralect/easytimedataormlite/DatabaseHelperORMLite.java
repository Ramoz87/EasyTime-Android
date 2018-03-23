package com.paralect.easytimedataormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.paralect.easytimedataormlite.model.Address;
import com.paralect.easytimedataormlite.model.Contact;
import com.paralect.easytimedataormlite.model.Customer;
import com.paralect.easytimedataormlite.model.Expense;
import com.paralect.easytimedataormlite.model.File;
import com.paralect.easytimedataormlite.model.Material;
import com.paralect.easytimedataormlite.model.Order;
import com.paralect.easytimedataormlite.model.Project;
import com.paralect.easytimedataormlite.model.Type;
import com.paralect.easytimedataormlite.model.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.paralect.datasource.ormlite.ORMLiteDataSource;

import java.sql.SQLException;

/**
 * Created by alexei on 03.01.2018.
 */

public class DatabaseHelperORMLite extends ORMLiteDataSource {

    private static final String DATABASE_NAME = "easy_time_db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelperORMLite(@NonNull Context context) {
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
            TableUtils.createTableIfNotExists(connectionSource, Expense.class);
            TableUtils.createTableIfNotExists(connectionSource, Contact.class);
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
            TableUtils.dropTable(connectionSource, Expense.class, true);
            TableUtils.dropTable(connectionSource, Contact.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        DaoManager.clearCache();
        super.close();
    }

    public Dao<Address, Long> getAddressDao() throws SQLException {
        return getDao(Address.class);
    }

    public Dao<User, String> getUserDao() throws SQLException {
        return getDao(User.class);
    }

    public Dao<Type, String> getTypeDao() throws SQLException {
        return getDao(Type.class);
    }

    public Dao<Expense, Long> getExpenseDao() throws SQLException {
        return getDao(Expense.class);
    }

    public Dao<Customer, String> getCustomerDao() throws SQLException {
        return getDao(Customer.class);
    }

    public Dao<Material, String> getMaterialDao() throws SQLException {
        return getDao(Material.class);
    }

    public Dao<Object, String> getObjectDao() throws SQLException {
        return getDao(Object.class);
    }

    public Dao<Order, String> getOrderDao() throws SQLException {
        return getDao(Order.class);
    }

    public Dao<Project, String> getProjectDao() throws SQLException {
        return getDao(Project.class);
    }

    public Dao<File, Long> getFileDao() throws SQLException {
        return getDao(File.class);
    }

    public Dao<Contact, Long> getContactDao() throws SQLException {
        return getDao(Contact.class);
    }
}
