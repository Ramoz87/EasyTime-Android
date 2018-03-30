package com.paralect.datasource.retrofit;

import com.google.gson.JsonElement;

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
    Single<JsonElement> get(@Url String url);

    @GET
    Single<List<JsonElement>> getList(@Url String url);

    @POST
    <T> Single<JsonElement> post(@Body T body);

    @FormUrlEncoded
    @POST
    Single<JsonElement> post(@Url String url, @FieldMap Map<String, String> fields);

    @PUT
    <T> Single<Void> put(@Body T body);

    @FormUrlEncoded
    @PUT
    Single<JsonElement> put(@Url String url, @FieldMap Map<String, String> fields);

    @PATCH
    <T> Single<JsonElement> patch(@Url String url, @Body T parameter);

    @DELETE
    Single<JsonElement> delete(@Url String url);
}
