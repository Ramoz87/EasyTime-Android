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

    <DS, AP> Single<AP> getAsync(EntityRequest<DS, AP, P> request);

    <DS, AP> Single<List<AP>> getList(EntityRequest<DS, AP, P> request);

    <DS, AP> Single<AP> saveAsync(EntityRequest<DS, AP, P> request);

    <DS, AP> Single<AP> saveOrUpdateAsync(EntityRequest<DS, AP, P> request);

    <DS, AP> Single<AP> updateAsync(EntityRequest<DS, AP, P> request);

    <DS, AP> Single<AP> deleteAsync(EntityRequest<DS, AP, P> request);

    <DS, AP> Single<Long> count(EntityRequest<DS, AP, P> request);
}
