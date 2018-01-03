package com.example.paralect.easytime;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by alexei on 27.12.2017.
 */

public final class ActionBarUtils {

    private ActionBarUtils() {

    }

    public static void setTitle(Activity activity, @StringRes int stringResId) {
        if (activity == null) return;

        if (!(activity instanceof AppCompatActivity)) return;

        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(stringResId);
        }
    }
}
