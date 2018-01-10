package com.example.paralect.easytime.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by alexei on 05.01.2018.
 */

public class ETPreferenceManager {

    private final String STORAGE_NAME = "EasyTimePreferences";
    private SharedPreferences etPreferences;

    private static final String KEY_LAUNCH_COUNT = "launch_count";

    private static ETPreferenceManager instance = null;

    public static ETPreferenceManager getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (ETPreferenceManager.class) {
                if (instance == null) {
                    instance = new ETPreferenceManager(context);
                }
            }
        }
        return instance;
    }

    private ETPreferenceManager(@NonNull Context context) {
        etPreferences = context.getApplicationContext().getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
    }

    public int getLaunchCount() {
        return etPreferences.getInt(KEY_LAUNCH_COUNT, 0);
    }

    public void plusLaunch() {
        int count = etPreferences.getInt(KEY_LAUNCH_COUNT, 0);
        etPreferences.edit().putInt(KEY_LAUNCH_COUNT, count + 1).apply();
    }

    public boolean isLaunchFirst() {
        return getLaunchCount() <= 1;
    }
}
