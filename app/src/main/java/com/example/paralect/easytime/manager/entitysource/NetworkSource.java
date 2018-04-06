package com.example.paralect.easytime.manager.entitysource;

import com.paralect.datasource.retrofit.DownloadFileRequest;
import com.paralect.easytimedataretrofit.NetworkHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.paralect.easytime.EasyTimeApplication.getContext;

/**
 * Created by Oleg Tarashkevich on 06/04/2018.
 */

public class NetworkSource  {

    private final String USERS_URL = "https://api.backendless.com/3AF81CDE-075B-72EF-FF10-2BD8170BA800/3884AF06-8F57-7BD9-FF13-3FDE4BBE8F00/files/users.csv";

    public void extractUsers(){

        NetworkHelper networkHelper = new NetworkHelper();

        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(getContext());
        downloadFileRequest.setQuery(USERS_URL);

        networkHelper.getAsync(downloadFileRequest)
        .observeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        ;
    }
}
