package com.paralect.datacsv.request;

import com.example.paralect.easytime.model.User;
import com.paralect.datasource.core.EntityRequestImpl;

import java.io.File;

/**
 * Created by Oleg Tarashkevich on 05/04/2018.
 */

public class UserRequestCSV extends EntityRequestImpl<String[], User, File> {

    public UserRequestCSV() {
        setQuery("db/users.csv");
    }

    @Override
    public User toAppEntity(String[] fields) {
        User user = new User();
        user.setUserId(fields[0]);
        user.setFirstName(fields[1]);
        user.setLastName(fields[2]);
        user.setUserName(fields[3]);
        user.setPassword(fields[4]);
        return user;
    }

    @Override
    public String[] toDataSourceEntity(User user) {
        return new String[0];
    }

    @Override
    public Class<String[]> getDataSourceEntityClazz() {
        return String[].class;
    }

    @Override
    public Class<User> getAppEntityClazz() {
        return User.class;
    }

}
