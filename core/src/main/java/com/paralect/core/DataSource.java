package com.paralect.core;

import android.util.SparseArray;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

/**
 * @param <M> type of model
 * @param <P> type of parameter for query purposes
 */
public interface DataSource<P> {

    <M extends Model> M getModel(Class<M> type, P parameter) throws Throwable;

    <M extends Model> List<M> getModels(Class<M> type, P parameter) throws Throwable;

    <M extends Model> void saveModel(Class<M> type, M model) throws Throwable;

    <M extends Model> void deleteModel(Class<M> type, M model) throws Throwable;

}
