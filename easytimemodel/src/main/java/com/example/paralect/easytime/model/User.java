package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexei on 26.12.2017.
 */

public class User implements Parcelable {

    private String firstName;
    private String lastName;
    private String password;
    private String userId;
    private String userName;

    public User() {

    }

    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        password = in.readString();
        userId = in.readString();
        userName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(password);
        dest.writeString(userId);
        dest.writeString(userName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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
