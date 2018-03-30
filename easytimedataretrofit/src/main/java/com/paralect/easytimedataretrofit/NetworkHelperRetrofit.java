package com.paralect.easytimedataretrofit;

import com.paralect.datasource.retrofit.RetrofitDataSource;

/**
 * Created by Oleg Tarashkevich on 30/03/2018.
 */

public class NetworkHelperRetrofit extends RetrofitDataSource {

    @Override
    protected String getBaseUrl() {
        return "https://www.instagram.com";
    }
}
