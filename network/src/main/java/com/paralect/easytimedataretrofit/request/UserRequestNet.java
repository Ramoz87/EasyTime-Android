package com.paralect.easytimedataretrofit.request;

import com.example.paralect.easytime.model.User;
import com.paralect.datasource.network.NetworkRequest;
import com.paralect.easytimedataretrofit.model.UserEntity;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class UserRequestNet extends NetworkRequest<UserEntity, User, Object> {

    @Override
    public User toAppEntity(UserEntity ex) {
        if (ex == null) return null;

        User in = new User();
        in.setFirstName(ex.graphql.user.fullName);
        in.setLastName(ex.graphql.user.userName);
        in.setUserId(ex.graphql.user.id);
        in.setUserName(ex.graphql.user.userName);
        return in;
    }

    @Override
    public UserEntity toDataSourceEntity(User in) {
        return null;
    }

    @Override
    public Class<UserEntity> getDataSourceEntityClazz() {
        return UserEntity.class;
    }

    @Override
    public Class<User> getAppEntityClazz() {
        return User.class;
    }

    public void queryGet() {
        setQuery("/sun/?__a=1");
    }
}