package com.paralect.core;

import android.util.SparseArray;

import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

/**
 * @param <M> type of model
 * @param <P> type of parameter for query purposes
 */
public interface DataSource<M extends Model, T, P> {

    M getModel(T type, P parameter) throws Throwable;

    List<M> getModels(T type, P parameter) throws Throwable;

    void saveModel(T type, M model) throws Throwable;

    void deleteModel(T type, M model) throws Throwable;

}
