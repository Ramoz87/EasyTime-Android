package com.example.paralect.easytime.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.paralect.easytime.BuildConfig;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.tutorial.TutorialActivity;
import com.example.paralect.easytime.manager.ETPreferenceManager;
import com.example.paralect.easytime.manager.entitysource.CSVSource;
import com.example.paralect.easytime.manager.entitysource.JobSource;
import com.example.paralect.easytime.manager.entitysource.MaterialsSource;
import com.example.paralect.easytime.manager.entitysource.EntityFactory;
import com.example.paralect.easytime.model.Constants;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.User;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.TinyDB;
import com.paralect.easytimedataormlite.DatabaseHelper;
import com.paralect.easytimedataormlite.request.UserRequestORM;
import com.paralect.easytimedataretrofit.NetworkHelper;
import com.paralect.easytimedataretrofit.request.UserRequestNet;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

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

//        getAndSaveUser();

        init();
    }

    // region Test
    private void init(){
        ETPreferenceManager preferenceManager = ETPreferenceManager.getInstance(this);
        preferenceManager.plusLaunch();
        // Download scv files, extract to database
        if (BuildConfig.DEBUG && preferenceManager.isLaunchFirst()) {
            downloadAll();
        }else{
            updateMyMaterials();
            launchMainScreen();
        }
    }

    public void downloadAll() {
        EntityFactory entityFactory = new EntityFactory();
        entityFactory.download()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<String>() {
                    @Override
                    public void onNext(String url) {
                        Logger.d("Downloaded: ", url);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e(throwable);
                    }

                    @Override
                    public void onComplete() {
                        updateMyMaterials();
                        launchMainScreen();
                    }
                });
    }

    private void updateMyMaterials() {
        ETPreferenceManager preferenceManager = ETPreferenceManager.getInstance(this);
        boolean firstLaunchInDay = preferenceManager.isCurrentLaunchFirstInDay();
        if (firstLaunchInDay) {
            Log.d(TAG, "First launch in a day, cleaning stock of materials");
            MaterialsSource materialsSource = new MaterialsSource();
            materialsSource.deleteMyMaterials();
        } else {
            Log.d(TAG, "not a first launch in a day");
        }
        preferenceManager.saveCurrentLaunchDate();
    }

    private void launchMainScreen() {
        Intent intent = null;
        TinyDB tinyDB = new TinyDB(LaunchScreenActivity.this);
        boolean isLaunched = tinyDB.getBoolean(Constants.TUTORIAL_LAUNCH, false);
        if (isLaunched)
            intent = new Intent(LaunchScreenActivity.this, LoginActivity.class);
        else
            intent = new Intent(LaunchScreenActivity.this, TutorialActivity.class);
        startActivity(intent);
    }

    public void getAndSaveUser() {

        final DatabaseHelper database = new DatabaseHelper(this);
        final NetworkHelper network = new NetworkHelper();

        final UserRequestORM userRequest = new UserRequestORM();
        final UserRequestNet userNetRequest = new UserRequestNet();
        userNetRequest.queryGet();

        // get from network
        network.getAsync(userNetRequest)
                // save to database
                .map(new Function<User, User>() {
                    @Override
                    public User apply(User user) throws Exception {
                        userRequest.setEntity(user);
                        database.saveOrUpdate(userRequest);
                        return user;
                    }
                })
                // retrieve from database
                .map(new Function<User, List<User>>() {
                    @Override
                    public List<User> apply(User user) throws Exception {
//                        userRequest.queryForId(user.getUserId());
                        userRequest.queryForAll();
                        return database.getList(userRequest);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<User> users) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
    // endregion

    // region Launch screen
//    private void init() {
//        Completable completable = Completable.fromCallable(new Callable<Void>() {
//            @Override
//            public Void call() throws Exception {
//                setupDataBase();
//                return null;
//            }
//        });
//
//        completable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        launchMainScreen();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) {
//                        Logger.d(TAG, throwable.getMessage());
//                        Logger.e(throwable);
//                    }
//                });
//    }
//
//    private void setupDataBase() {
//        ETPreferenceManager preferenceManager = ETPreferenceManager.getInstance(this);
//        preferenceManager.plusLaunch();
//        if (BuildConfig.DEBUG && preferenceManager.isLaunchFirst()) { // pre-populate data from assets
//            Log.d(TAG, "filling data from db");
//            CSVSource csvSource = new CSVSource();
//            csvSource.populateData(getAssets());
//        } else {
//            JobSource jobSource = new JobSource();
//            List<Job> jobs = jobSource.getAllJobs();
//            for (Job job : jobs) {
//                // Log.d(TAG, "date for job: " + job.getDateString());
//            }
//        }
//        updateMyMaterials();
//    }


    // endregion
}
