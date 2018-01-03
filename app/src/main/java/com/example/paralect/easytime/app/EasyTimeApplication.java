package com.example.paralect.easytime.app;

import android.app.Application;

import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.DatabaseHelper;
import com.example.paralect.easytime.model.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by alexei on 26.12.2017.
 */

public class EasyTimeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        fillData();
    }

    private void fillData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        try {
            Dao<Address, Long> addressDao = databaseHelper.getAddressDao();
            Address address = new Address();

            Dao<User, Long> userDao = databaseHelper.getUserDao();
            User user = new User();
            user.setUserId(1);
            user.setFirstName("User name");
            user.setLastName("User last name");
            userDao.create(user);
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
