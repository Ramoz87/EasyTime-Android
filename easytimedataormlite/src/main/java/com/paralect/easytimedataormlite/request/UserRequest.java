package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.paralect.easytimedataormlite.model.UserEntity;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class UserRequest extends BaseRequest<UserEntity, User> {

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

    public void queryForId(OrmLiteSqliteOpenHelper helper, String id) throws SQLException {
        queryWhere(helper, UserEntity.ID, id);
    }
}