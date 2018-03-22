package com.paralect.datasource.core;

import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

/**
 *  param <M> type of model
 * @param <P> type of parameter for query purposes
 */
public interface DataSource<P> {

    <M extends Entity> M get(Class<M> type, P parameter) throws Throwable;

    <M extends Entity> List<M> getList(Class<M> type, P parameter) throws Throwable;

    <M extends Entity> void save(Class<M> type, M model) throws Throwable;

    <M extends Entity> void update(Class<M> type, M model) throws Throwable;

    <M extends Entity> void delete(Class<M> type, M model) throws Throwable;

}
