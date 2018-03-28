package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "users")
public class UserEntity {

    public static final String ID = "userId";

    @DatabaseField(columnName = "firstName")
    private String firstName;
    @DatabaseField(columnName = "lastName")
    private String lastName;
    @DatabaseField(columnName = "password")
    private String password;

    @DatabaseField(columnName = ID, id = true)
    private String userId;
    @DatabaseField(columnName = "username")
    private String userName;

    public UserEntity() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
