package com.paralect.database.request;

import com.example.paralect.easytime.model.User;
import com.paralect.datasource.ormlite.ORMLiteRequest;
import com.paralect.database.model.UserEntity;

import java.sql.SQLException;

import static com.example.paralect.easytime.model.Constants.USER_ID;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class UserRequestORM extends ORMLiteRequest<UserEntity, User> {

    @Override
    public User toAppEntity(UserEntity ex) {
        if (ex == null) return null;

        User in = new User();
        in.setFirstName(ex.getFirstName());
        in.setLastName(ex.getLastName());
        in.setPassword(ex.getPassword());
        in.setUserId(ex.getUserId());
        in.setUserName(ex.getUserName());
        return in;
    }

    @Override
    public UserEntity toDataSourceEntity(User in) {
        if (in == null) return null;

        UserEntity ex = new UserEntity();
        ex.setFirstName(in.getFirstName());
        ex.setLastName(in.getLastName());
        ex.setPassword(in.getPassword());
        ex.setUserId(in.getUserId());
        ex.setUserName(in.getUserName());
        return ex;
    }

    @Override
    public Class<UserEntity> getDataSourceEntityClazz() {
        return UserEntity.class;
    }

    @Override
    public Class<User> getAppEntityClazz() {
        return User.class;
    }

    @Override
    public void queryForId(String id) throws SQLException {
        queryWhere(USER_ID, id);
    }
}