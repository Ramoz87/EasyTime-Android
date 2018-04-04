package com.example.paralect.easytime.utils;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

/**
 * Created by Oleg Tarashkevich on 17.01.16.
 */
public class Logger {

    public static final String TAG = "EasyTime ";

    private static boolean enable = false;

    public static void setEnabled(boolean enable) {
        Logger.enable = enable;
    }

    public static boolean isEnabled() {
        return enable;
    }

    public static void d(String key, String message) {
        if (enable)
            Log.d(TAG + key, message);
    }

    public static void d(String message) {
        if (enable)
            Log.d(TAG, message);
    }

    public static void separator() {
        if (enable)
            Log.d(TAG, "-----------------------------------------");
    }

    /**
     * For long messages
     */
    public static void log(String key, String message) {
        if (enable) {
            // Split by line, then ensure each line can fit into Log's maximum length.
            for (int i = 0, length = message.length(); i < length; i++) {
                int newline = message.indexOf('\n', i);
                newline = newline != -1 ? newline : length;
                do {
                    int end = Math.min(newline, i + 4000);
                    Log.d(TAG + key, message.substring(i, end));
                    i = end;
                } while (i < newline);
            }
        }
    }

    public static void w(String msg) {
        if (enable)
            Log.w(TAG, msg);
    }

    public static void i(String msg) {
        if (enable)
            Log.i(TAG, msg);
    }

    /**
     * Log Exception
     */

    public static void e(Throwable e) {
        if (enable)
            e.printStackTrace();
        logCrashlytics(e);
    }

    public static void e(String msg) {
        if (enable)
            e(TAG, msg);
    }

    public static void e(String message, Throwable e) {
        e(TAG, message, e);
    }

    public static void e(String tag, String message, Throwable e) {
        if (enable)
            Log.e(tag, message, e);
        logCrashlytics(message);
        logCrashlytics(e);
    }

    public static void e(String tag, String message) {
        if (enable)
            Log.e(TAG + tag, message);
        Exception exception = new Exception(message);
        logCrashlytics(message);
        logCrashlytics(exception);

    }

    public static void wtf(String message, Throwable e) {
        if (enable)
            Log.wtf(TAG, message, e);
        logCrashlytics(e);
    }

    private static void logCrashlytics(String message) {
        Crashlytics.log(message);
    }

    private static void logCrashlytics(Throwable e) {
        Crashlytics.logException(e);
    }

}