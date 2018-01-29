package com.example.paralect.easytime.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.paralect.easytime.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alexei on 05.01.2018.
 */

public class ETPreferenceManager {

    private final String STORAGE_NAME = "EasyTimePreferences";
    private SharedPreferences etPreferences;

    private static final String KEY_LAUNCH_COUNT = "launch_count";
    private static final String KEY_LAST_DATE_YEAR = "last_date_year";
    private static final String KEY_LAST_DATE_MONTH = "last_date_month";
    private static final String KEY_LAST_DATE_DAY = "last_date_day";

    /*user preferences*/
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_ID = "user_password";

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

    public void saveCurrentLaunchDate() {
        Calendar calendar = Calendar.getInstance();
        etPreferences.edit()
                .putInt(KEY_LAST_DATE_YEAR, calendar.get(Calendar.YEAR))
                .putInt(KEY_LAST_DATE_MONTH, calendar.get(Calendar.MONTH))
                .putInt(KEY_LAST_DATE_DAY, calendar.get(Calendar.DAY_OF_MONTH))
                .apply();
    }

    public boolean isCurrentLaunchFirstInDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int lastYear = etPreferences.getInt(KEY_LAST_DATE_YEAR, year);
        int lastMonth = etPreferences.getInt(KEY_LAST_DATE_MONTH, month);
        int lastDay = etPreferences.getInt(KEY_LAST_DATE_DAY, day);
        return !(year == lastYear && month == lastMonth && day == lastDay);
    }

    public String getUserName() {
        return etPreferences.getString(KEY_USER_NAME, null);
    }

    public String getUserId() {
        return etPreferences.getString(KEY_USER_ID, null);
    }

    public void saveUser(User user) {
        String userId = user != null ? user.getUserId() : null;
        etPreferences.edit()
                .putString(KEY_USER_ID, userId)
                .apply();
    }
}
