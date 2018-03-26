package com.paralect.datasource.core;

import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

/**
 * param <IN> Internal, entity of data source
 * param <EX> External, entity of application
 *
 * @param <P> Parameter for communication with data source
 */

public interface DataSource<P> {

    <IN, EX> IN get(EntityRequest<IN, EX, P> request) throws Throwable;

    <IN, EX> List<IN> getList(EntityRequest<IN, EX, P> request) throws Throwable;

    <IN, EX> void save(EntityRequest<IN, EX, P> request) throws Throwable;

    <IN, EX> void update(EntityRequest<IN, EX, P> request) throws Throwable;

    <IN, EX> void delete(EntityRequest<IN, EX, P> request) throws Throwable;
}
