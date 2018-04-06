package com.paralect.easytimedataretrofit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexei on 26.12.2017.
 */

public class UserEntity {

    public graphql graphql;
    
    public static class graphql{
      public User user;
    }

    public static class User{

        @SerializedName("id")
        public String id;

        @SerializedName("full_name")
        public String fullName;

        @SerializedName("username")
        public String userName;
    }
}
