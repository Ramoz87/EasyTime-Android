package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.paralect.easytimedataormlite.model.UserEntity;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class UserRequest extends BaseRequest<User, UserEntity> {

    @Override
    public User toInternalEntity(UserEntity ex) {
        User in = new User();
        if (ex != null) {
            in.setFirstName(ex.getFirstName());
            in.setLastName(ex.getLastName());
            in.setPassword(ex.getPassword());
            in.setUserId(ex.getUserId());
            in.setUserName(ex.getUserName());
        }
        return in;
    }

    @Override
    public UserEntity toExternalEntity(User in) {
        UserEntity ex = new UserEntity();
        if (in != null) {
            ex.setFirstName(in.getFirstName());
            ex.setLastName(in.getLastName());
            ex.setPassword(in.getPassword());
            ex.setUserId(in.getUserId());
            ex.setUserName(in.getUserName());
        }
        return ex;
    }

    @Override
    public Class<User> getInnerEntityClazz() {
        return User.class;
    }

    @Override
    public Class<UserEntity> getExternalEntityClazz() {
        return UserEntity.class;
    }

    public void queryForId(OrmLiteSqliteOpenHelper helper, String id) throws SQLException {
        queryWhere(helper, UserEntity.ID, id);
    }
}