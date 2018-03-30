package com.paralect.datasource.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.paralect.datasource.core.EntityRequest;
import com.paralect.datasource.rx.DataSourceRx;

import java.util.List;
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

public abstract class RetrofitDataSource implements DataSourceRx<String> {

    protected RetrofitService service;

    public RetrofitDataSource() {
        service = createRetrofitService();
    }

    // region Asynchronous access
    @Override
    public <DS, AP> Single<AP> getAsync(final EntityRequest<DS, AP, String> request) {
        Single<JsonElement> networkData = service.get(request.getParameter());
        Single<AP> appData = networkData.map(new Function<JsonElement, AP>() {
            @Override
            public AP apply(JsonElement jsonElement) throws Exception {
                DS ds = getGson().fromJson(jsonElement, request.getDataSourceEntityClazz());
                AP ap = request.toAppEntity(ds);
                return ap;
            }
        });
        return appData;
    }

    @Override
    public <DS, AP> Single<List<AP>> getList(EntityRequest<DS, AP, String> request) {
        return null;
    }

    @Override
    public <DS, AP> Single<Object> saveAsync(EntityRequest<DS, AP, String> request) {
        return null;
    }

    @Override
    public <DS, AP> Single<Object> updateAsync(EntityRequest<DS, AP, String> request) {
        return null;
    }

    @Override
    public <DS, AP> Single<Object> deleteAsync(EntityRequest<DS, AP, String> request) {
        return null;
    }
    // endregion

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
        return new Gson();
    }

    protected abstract String getBaseUrl();

}
