package com.paralect.datasource.retrofit;

import com.paralect.datasource.core.EntityRequestImpl;

/**
 * Created by Oleg Tarashkevich on 27/03/2018.
 */

public abstract class RetrofitRequest<DS, AP> extends EntityRequestImpl<DS, AP, String> {

    public void queryGet(String url) {
        setParameter(url);
    }

}
