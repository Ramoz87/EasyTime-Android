package com.paralect.core;

import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

/**
 * @param <M> type of model
 * @param <P> type of parameter for query purposes
 */
public interface DataSource<P> {

    <M extends Model> M get(Class<M> type, P parameter) throws Throwable;

    <M extends Model> List<M> getList(Class<M> type, P parameter) throws Throwable;

    <M extends Model> void save(Class<M> type, M model) throws Throwable;

    <M extends Model> void update(Class<M> type, M model) throws Throwable;

    <M extends Model> void delete(Class<M> type, M model) throws Throwable;

}
