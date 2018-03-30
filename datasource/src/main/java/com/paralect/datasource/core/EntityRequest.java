package com.paralect.datasource.core;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

/**
 * @param <DS> Entity of data source
 * @param <AP> Entity of application
 * @param <P>  Parameter (Object) for communication with data source
 *             Query (String) for communication with data source
 */
public interface EntityRequest<DS, AP, P> extends EntityMapper<DS, AP>, EntityQuery<P> {

}
