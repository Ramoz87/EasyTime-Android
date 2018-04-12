package com.paralect.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.paralect.datasource.ormlite.ORMLiteDataSource;
import com.paralect.database.model.AddressEntity;
import com.paralect.database.model.ContactEntity;
import com.paralect.database.model.CustomerEntity;
import com.paralect.database.model.ExpenseEntity;
import com.paralect.database.model.FileEntity;
import com.paralect.database.model.MaterialEntity;
import com.paralect.database.model.ObjectEntity;
import com.paralect.database.model.OrderEntity;
import com.paralect.database.model.ProjectEntity;
import com.paralect.database.model.TypeEntity;
import com.paralect.database.model.UserEntity;

import java.sql.SQLException;

import static com.example.paralect.easytime.model.Constants.DATABASE_NAME;
import static com.example.paralect.easytime.model.Constants.DATABASE_VERSION;

/**
 * Created by alexei on 03.01.2018.
 */

public class DatabaseHelper extends ORMLiteDataSource {


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
