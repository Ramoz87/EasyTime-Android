package com.paralect.core.rx;

import com.paralect.core.DataSource;
import com.paralect.core.Model;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

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
