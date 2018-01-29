package com.example.paralect.easytime;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.paralect.easytime.utils.Logger;

/**
 * Created by alexei on 26.12.2017.
 */

public class EasyTimeApplication extends Application {

    public static final String TAG = "EasyTime";

    @SuppressLint("StaticFieldLeak") private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        // TODO disable on release version!!!
        Logger.setEnabled(BuildConfig.DEBUG);
    }

    public static Context getContext() {
        return sContext;
    }

}
