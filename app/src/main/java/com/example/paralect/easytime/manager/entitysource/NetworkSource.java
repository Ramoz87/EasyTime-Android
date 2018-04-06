package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.model.User;
import com.paralect.datacsv.CSVHelper;
import com.paralect.datacsv.request.UserRequestCSV;
import com.paralect.datasource.retrofit.DownloadFileRequest;
import com.paralect.easytimedataormlite.request.UserRequestORM;
import com.paralect.easytimedataretrofit.NetworkHelper;

import java.io.File;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.paralect.easytime.EasyTimeApplication.getContext;
import static com.paralect.datasource.rx.DataSourceRx.NOTHING;

/**
 * Created by Oleg Tarashkevich on 06/04/2018.
 */

public class NetworkSource extends CSVSource {

    private final String USERS_URL = "https://api.backendless.com/3AF81CDE-075B-72EF-FF10-2BD8170BA800/3884AF06-8F57-7BD9-FF13-3FDE4BBE8F00/files/users.csv";

    private final NetworkHelper networkHelper = new NetworkHelper();
    private final CSVHelper csvFileHelper = new CSVHelper();

    public void extractUsers() {

        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(getContext());
        downloadFileRequest.setQuery(USERS_URL);

        // Download file
        networkHelper.getDataAsync(downloadFileRequest)
                // convert from csv to list
                .map(new Function<File, List<User>>() {
                    @Override
                    public List<User> apply(File file) throws Exception {
                        UserRequestCSV userRequestCSV = new UserRequestCSV();
                        userRequestCSV.setParameter(file);
                        return csvFileHelper.getList(userRequestCSV);
                    }
                })
                // save list to database
                .map(new Function<List<User>, Object>() {
                    @Override
                    public Object apply(List<User> users) throws Exception {
                        UserRequestORM requestORM = new UserRequestORM();
                        fillData(users, requestORM);
                        return NOTHING;
                    }
                })
                // get list from database
                .map(new Function<Object, List<User>>() {
                    @Override
                    public List<User> apply(Object o) throws Exception {
                        UserRequestORM requestORM = new UserRequestORM();
                        requestORM.queryForAll();
                        return dataSource.getList(requestORM);
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
}
