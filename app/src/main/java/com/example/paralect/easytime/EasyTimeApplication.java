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
        boolean debug = true;//BuildConfig.DEBUG;
        CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(debug).build();
        Crashlytics crashlytics = new Crashlytics.Builder().core(core).build();
        Fabric.with(this, crashlytics);

        Logger.setEnabled(debug);

        // TODO: Test
//        try {
//            Expense expense = Expense.createTimeExpense("123", "time-exp", 2, 15);
//            Expense saved = DataManager.getInstance().saveAndGetExpense(expense);
//            List<Expense> expenses = DataManager.getInstance().getTimeExpenses("123");
//
//            expense.setExpenseId(21);
////            expense.setName("HFHLKSLK");
////            DataManager.getInstance().updateExpense(expense);
//            DataManager.getInstance().deleteExpense(expense);
//            expenses = DataManager.getInstance().getTimeExpenses("123");
//            Logger.separator();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public static Context getContext() {
        return sContext;
    }

    private void breakDown() {
        throw new RuntimeException("For crashlytics");
    }
}
