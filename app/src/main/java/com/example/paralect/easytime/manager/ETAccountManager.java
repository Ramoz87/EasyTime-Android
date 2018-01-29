package com.example.paralect.easytime.manager;

import android.content.Context;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.model.User;

/**
 * Created by alexei on 29.01.2018.
 */

public final class ETAccountManager {
    private static final String TAG = ETAccountManager.class.getSimpleName();

    private static ETAccountManager instance = null;

    public static ETAccountManager getInstance() {
        if (instance == null) {
            synchronized (ETAccountManager.class) {
                if (instance == null) {
                    instance = new ETAccountManager();
                }
            }
        }
        return instance;
    }

    private User user;
    private ETPreferenceManager preferenceManager;
    private EasyTimeManager easyTimeManager;

    private ETAccountManager() {
        Context context = EasyTimeApplication.getContext();
        preferenceManager = ETPreferenceManager.getInstance(context);
        String userId = preferenceManager.getUserId();
        if (userId == null) {
            user = null;
        } else {
            easyTimeManager = EasyTimeManager.getInstance();
            user = easyTimeManager.getUser(userId);
        }
    }

    public void login(User user) {
        this.user = user;
        preferenceManager.saveUser(user);
    }

    public User logout() {
        User old = user;
        user = null;
        preferenceManager.saveUser(user);
        return old;
    }

    public boolean isLoggedIn() {
        return user != null;
    }
}
