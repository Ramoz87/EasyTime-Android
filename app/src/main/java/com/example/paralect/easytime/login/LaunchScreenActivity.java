package com.example.paralect.easytime.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.paralect.easytime.BuildConfig;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.tutorial.TutorialActivity;
import com.example.paralect.easytime.manager.ETPreferenceManager;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Constants;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.TinyDB;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.paralect.easytime.EasyTimeApplication.TAG;

/**
 * Created by Oleg Tarashkevich on 29.01.2018.
 */

public class LaunchScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
//        init();

          EasyTimeManager.getInstance().getUser();
    }

    private void init() {
        Completable completable = Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                setupDataBase();
                return null;
            }
        });

        completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Intent intent = null;
                        TinyDB tinyDB = new TinyDB(LaunchScreenActivity.this);
                        boolean isLaunched = tinyDB.getBoolean(Constants.TUTORIAL_LAUNCH, false);
                        if (isLaunched)
                            intent = new Intent(LaunchScreenActivity.this, LoginActivity.class);
                        else
                            intent = new Intent(LaunchScreenActivity.this, TutorialActivity.class);
                        startActivity(intent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.d(TAG, throwable.getMessage());
                        Logger.e(throwable);
                    }
                });
    }

    private void setupDataBase() {
        ETPreferenceManager preferenceManager = ETPreferenceManager.getInstance(this);
        preferenceManager.plusLaunch();
        if (BuildConfig.DEBUG && preferenceManager.isLaunchFirst()) { // pre-populate data from assets
            Log.d(TAG, "filling data from db");
            EasyTimeManager.getInstance().populateData(getAssets());
        } else {
            List<Job> jobs = EasyTimeManager.getInstance().getAllJobs();
            for (Job job : jobs) {
                // Log.d(TAG, "date for job: " + job.getDateString());
            }
        }

        updateMyMaterials();
    }

    private void updateMyMaterials() {
        ETPreferenceManager preferenceManager = ETPreferenceManager.getInstance(this);
        boolean firstLaunchInDay = preferenceManager.isCurrentLaunchFirstInDay();
        if (firstLaunchInDay) {
            Log.d(TAG, "First launch in a day, cleaning stock of materials");
            EasyTimeManager.getInstance().deleteMyMaterials();
        } else {
            Log.d(TAG, "not a first launch in a day");
        }
        preferenceManager.saveCurrentLaunchDate();
    }
}
