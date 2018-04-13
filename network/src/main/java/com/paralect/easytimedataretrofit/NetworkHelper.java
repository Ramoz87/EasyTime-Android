package com.paralect.easytimedataretrofit;

import com.paralect.datasource.retrofit.RetrofitDataSource;

/**
 * Created by Oleg Tarashkevich on 30/03/2018.
 */

public class NetworkHelper extends RetrofitDataSource {

    @Override
    public String getBaseUrl() {
        return "https://www.instagram.com";
    }

    @Override
    public boolean enableLogging() {
        return true;
    }
}
