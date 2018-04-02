package com.paralect.datasource.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.paralect.datasource.core.EntityRequest;
import com.paralect.datasource.rx.DataSourceRx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public abstract class RetrofitDataSource implements DataSourceRx<Object> {

    protected RetrofitService service;
    protected Gson gson;

    public RetrofitDataSource() {
        service = createRetrofitService();
    }

    // region Asynchronous access
    @Override
    public <DS, AP> Single<AP> getAsync(final EntityRequest<DS, AP, Object> request) {
        return service.get(request.getQuery())
                .map(convertEntity(request));
    }

    @Override
    public <DS, AP> Single<List<AP>> getList(final EntityRequest<DS, AP, Object> request) {
        return service.get(request.getQuery())
                .map(new Function<JsonElement, List<AP>>() {
                    @Override
                    public List<AP> apply(JsonElement jsonElement) throws Exception {
                        List<DS> dsList = gson.fromJson(jsonElement, new TypeToken<List<DS>>() {
                        }.getType());
                        List<AP> apList = new ArrayList<>();
                        for (DS ds : dsList) {
                            AP ap = request.toAppEntity(ds);
                            apList.add(ap);
                        }
                        return apList;
                    }
                });
    }

    @Override
    public <DS, AP> Single<AP> saveAsync(final EntityRequest<DS, AP, Object> request) {
        Single<JsonElement> single = null;
        Object parameter = request.getParameter();
        Map<String, String> map = request.getMap();
        if (map != null && !map.isEmpty())
            single = service.post(request.getQuery(), map);
        else if (parameter != null)
            single = service.post(request.getQuery(), parameter);
        else
            single = service.post(request.getQuery());
        return single.map(convertEntity(request));
    }

    @Override
    public <DS, AP> Single<AP> saveOrUpdateAsync(EntityRequest<DS, AP, Object> request) {
        Single<JsonElement> single = null;
        Object parameter = request.getParameter();
        Map<String, String> map = request.getMap();
        if (map != null && !map.isEmpty())
            single = service.put(request.getQuery(), map);
        else if (parameter != null)
            single = service.put(request.getQuery(), parameter);
        else
            single = service.put(request.getQuery());
        return single.map(convertEntity(request));
    }

    @Override
    public <DS, AP> Single<AP> updateAsync(EntityRequest<DS, AP, Object> request) {
        Single<JsonElement> single = null;
        Object parameter = request.getParameter();
        Map<String, String> map = request.getMap();
        if (map != null && !map.isEmpty())
            single = service.patch(request.getQuery(), map);
        else if (parameter != null)
            single = service.patch(request.getQuery(), parameter);
        else
            single = service.patch(request.getQuery());
        return single.map(convertEntity(request));
    }

    @Override
    public <DS, AP> Single<AP> deleteAsync(EntityRequest<DS, AP, Object> request) {
        return service.delete(request.getQuery())
                .map(convertEntity(request));
    }

    @Override
    public <DS, AP> Single<Long> count(EntityRequest<DS, AP, Object> request) {
        return service.count(request.getQuery());
    }

    // endregion

    public <DS, AP> Function<JsonElement, AP> convertEntity(final EntityRequest<DS, AP, Object> request) {
        return new Function<JsonElement, AP>() {
            @Override
            public AP apply(JsonElement jsonElement) throws Exception {
                DS ds = getGson().fromJson(jsonElement, request.getDataSourceEntityClazz());
                AP ap = request.toAppEntity(ds);
                return ap;
            }
        };
    }

    protected RetrofitService createRetrofitService() {
        GsonConverterFactory factory = GsonConverterFactory.create(getGson());

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2ErrorHandlingCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient());

        Retrofit retrofit = builder.build();
        return retrofit.create(RetrofitService.class);
    }

    protected OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(getLoginInterceptor());

        return builder.build();
    }

    protected Interceptor getLoginInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    protected Gson getGson() {
        if (gson == null)
            gson = new Gson();
        return gson;
    }

    protected abstract String getBaseUrl();

}
