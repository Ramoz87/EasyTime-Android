package com.paralect.easytimedataormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.paralect.datasource.ormlite.ORMLiteDataSource;
import com.paralect.easytimedataormlite.model.AddressEntity;
import com.paralect.easytimedataormlite.model.ContactEntity;
import com.paralect.easytimedataormlite.model.CustomerEntity;
import com.paralect.easytimedataormlite.model.ExpenseEntity;
import com.paralect.easytimedataormlite.model.FileEntity;
import com.paralect.easytimedataormlite.model.MaterialEntity;
import com.paralect.easytimedataormlite.model.ObjectEntity;
import com.paralect.easytimedataormlite.model.OrderEntity;
import com.paralect.easytimedataormlite.model.ProjectEntity;
import com.paralect.easytimedataormlite.model.TypeEntity;
import com.paralect.easytimedataormlite.model.UserEntity;

import java.sql.SQLException;

/**
 * Created by alexei on 03.01.2018.
 */

public class DatabaseHelper extends ORMLiteDataSource {

    private static final String DATABASE_NAME = "easy_time_db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, AddressEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, TypeEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, CustomerEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, MaterialEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ObjectEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, OrderEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ProjectEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, FileEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ExpenseEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ContactEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, AddressEntity.class, true);
            TableUtils.dropTable(connectionSource, UserEntity.class, true);
            TableUtils.dropTable(connectionSource, TypeEntity.class, true);
            TableUtils.dropTable(connectionSource, CustomerEntity.class, true);
            TableUtils.dropTable(connectionSource, MaterialEntity.class, true);
            TableUtils.dropTable(connectionSource, ObjectEntity.class, true);
            TableUtils.dropTable(connectionSource, OrderEntity.class, true);
            TableUtils.dropTable(connectionSource, ProjectEntity.class, true);
            TableUtils.dropTable(connectionSource, FileEntity.class, true);
            TableUtils.dropTable(connectionSource, ExpenseEntity.class, true);
            TableUtils.dropTable(connectionSource, ContactEntity.class, true);
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

}
