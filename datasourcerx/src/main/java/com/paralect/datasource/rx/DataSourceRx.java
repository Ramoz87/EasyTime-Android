package com.paralect.datasource.rx;

import com.paralect.datasource.core.EntityRequest;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Oleg Tarashkevich on 21/03/2018.
 */

/**
 * param <DS> Entity of data source
 * param <AP> Entity of application
 *
 * @param <P> Parameter for communication with data source
 */

public interface DataSourceRx<P> {

    Object NOTHING = new Object();

    <DS, AP> Single<AP> getAsync(EntityRequest<DS, AP, P> request) throws Throwable;

    <DS, AP> Single<List<AP>> getList(EntityRequest<DS, AP, P> request) throws Throwable;

    <DS, AP> Single<Object> saveAsync(EntityRequest<DS, AP, P> request) throws Throwable;

    <DS, AP> Single<Object> updateAsync(EntityRequest<DS, AP, P> request) throws Throwable;

    <DS, AP> Single<Object> deleteAsync(EntityRequest<DS, AP, P> request) throws Throwable;
}
