package com.paralect.datasource.rx;

import com.paralect.datasource.core.Model;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Oleg Tarashkevich on 21/03/2018.
 */

public interface DataSourceRx<P> {

    Object NOTHING = new Object();

    <M extends Model> Single<M> getAsync(final Class<M> type, final P parameter);

    <M extends Model> Single<List<M>> getListAsync(final Class<M> type, final P parameter);

    <M extends Model> Single<Object> saveAsync(final Class<M> type, final M model);

    <M extends Model> Single<Object> updateAsync(final Class<M> type, final M model);

    <M extends Model> Single<Object> deleteAsync(final Class<M> type, final M model);

}
