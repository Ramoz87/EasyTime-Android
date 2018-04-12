package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.User;
import com.example.paralect.easytime.utils.Logger;
import com.paralect.database.request.UserRequestORM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

public class UserSource extends EntitySource {

    public List<User> getMembers(Job job) throws SQLException {
        String[] ids = job.getMemberIds();
        if (ids == null || ids.length == 0) return null;
        List<User> users = new ArrayList<>();
        UserRequestORM userRequest = new UserRequestORM();

        for (String id : ids) {
            userRequest.queryForId(id);
            User user = dataSource.get(userRequest);
            users.add(user);
        }
        return users;
    }

    public User getUser(String userId) {
        try {
            UserRequestORM userRequest = new UserRequestORM();
            userRequest.queryForId(userId);
            return dataSource.get(userRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }

    public User getRandomUser() {
        try {
            UserRequestORM userRequest = new UserRequestORM();
            userRequest.queryForId("0be618c9-e68b-435a-bdf4-d7f4ee6b6ba4");
            return dataSource.get(userRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }

}
