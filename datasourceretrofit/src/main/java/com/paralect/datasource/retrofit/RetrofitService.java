package com.paralect.datasource.retrofit;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

/**
 * Created by Oleg Tarashkevich on 30/03/2018.
 */

public interface RetrofitService {

    @GET
    <T> Single<T> get(@Url String url);

    @GET
    <T> Single<List<T>> getList(@Url String url);

    @POST
    <T> Single<Void> post(@Body T body);

    @FormUrlEncoded
    @POST
    <T> Single<T> post(@Url String url, @FieldMap Map<String, String> fields);

    @PUT
    <T> Single<Void> put(@Body T body);

    @FormUrlEncoded
    @PUT
    <T> Single<T> put(@Url String url, @FieldMap Map<String, String> fields);

    @PATCH
    <T, P> Single<T> patch(@Url String url, @Body P parameter);

    @DELETE
    <T> Single<T> delete(@Url String url);
}
