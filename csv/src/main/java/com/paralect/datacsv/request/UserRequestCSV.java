package com.paralect.datacsv.request;

import com.example.paralect.easytime.model.User;

/**
 * Created by Oleg Tarashkevich on 05/04/2018.
 */

public class UserRequestCSV extends CSVRequest<User> {

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
    public Class<User> getAppEntityClazz() {
        return User.class;
    }

}
