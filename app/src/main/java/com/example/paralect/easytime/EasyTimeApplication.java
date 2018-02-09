package com.example.paralect.easytime;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.example.paralect.easytime.utils.Logger;

import io.fabric.sdk.android.Fabric;

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
        boolean debug = false;//BuildConfig.DEBUG;
        CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(debug).build();
        Crashlytics crashlytics = new Crashlytics.Builder().core(core).build();
        Fabric.with(this, crashlytics);

        Logger.setEnabled(debug);

    }

    public static Context getContext() {
        return sContext;
    }

    private void breakDown() {
        throw new RuntimeException("For crashlytics");
    }
}
