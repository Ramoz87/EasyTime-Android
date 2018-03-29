package com.paralect.datasource.core;

import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

/**
 * param <DS> Entity of data source
 * param <AP> Entity of application
 *
 * @param <P> Parameter for communication with data source
 */

public interface DataSource<P> {

    <DS, AP> AP get(EntityRequest<DS, AP, P> request) throws Throwable;

    <DS, AP> List<AP> getList(EntityRequest<DS, AP, P> request) throws Throwable;

    <DS, AP> void save(EntityRequest<DS, AP, P> request) throws Throwable;

    <DS, AP> void update(EntityRequest<DS, AP, P> request) throws Throwable;

    <DS, AP> void delete(EntityRequest<DS, AP, P> request) throws Throwable;
}
