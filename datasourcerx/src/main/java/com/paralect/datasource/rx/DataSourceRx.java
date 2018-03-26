package com.paralect.datasource.rx;

import com.paralect.datasource.core.EntityRequest;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Oleg Tarashkevich on 21/03/2018.
 */

/**
 * param <IN> Internal, entity of data source
 * param <EX> External, entity of application
 *
 * @param <P> Parameter for communication with data source
 */

public interface DataSourceRx<P> {

    Object NOTHING = new Object();

    <IN, EX> Single<IN> getAsync(final EntityRequest<IN, EX, P> request);

    <IN, EX> Single<List<IN>> getListAsync(final EntityRequest<IN, EX, P> request);

    <IN, EX> Single<Object> saveAsync(final EntityRequest<IN, EX, P> request);

    <IN, EX> Single<Object> updateAsync(final EntityRequest<IN, EX, P> request);

    <IN, EX> Single<Object> deleteAsync(final EntityRequest<IN, EX, P> request);

}
