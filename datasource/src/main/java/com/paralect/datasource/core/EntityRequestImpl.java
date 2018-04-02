package com.paralect.datasource.core;

/**
 * Created by Oleg Tarashkevich on 23/03/2018.
 */

import java.sql.SQLException;
import java.util.Map;

/**
 * @param <DS> Entity of data source
 * @param <AP> Entity of application
 * @param <P>  Parameter (Object) for communication with data source
 *             Query (String) for communication with data source
 */
public abstract class EntityRequestImpl<DS, AP, P> implements EntityRequest<DS, AP, P> {

    protected P mParameter;
    protected Map<String, String> mMap;
    protected String mQuery;
    protected AP mEntity;

    @Override
    public void setParameter(P parameter) {
        mParameter = parameter;
    }

    @Override
    public P getParameter() {
        return mParameter;
    }

    @Override
    public void setQuery(String query) {
        mQuery = query;
    }

    @Override
    public String getQuery() {
        return mQuery;
    }

    @Override
    public void setEntity(AP dsEntity) {
        mEntity = dsEntity;
    }

    @Override
    public AP getEntity() {
        return mEntity;
    }

    @Override
    public void setMap(Map<String, String> map) {
        mMap = map;
    }

    @Override
    public Map<String, String> getMap() {
        return mMap;
    }

    // region General methods
    public void queryForId(String id) throws Throwable {

    }

    public void queryForId(long id) throws Throwable {

    }

    public void queryForEqual(String id) throws Throwable {

    }

    public void queryForSearch(final String query) throws Throwable {

    }

    public <ID> void queryWhere(final String name, final ID value) throws Throwable {

    }

    public void queryForLast(final String orderByFieldName) throws Throwable {

    }

    public void queryForFirst(final String orderByFieldName) throws Throwable {

    }

    public void queryForCount(final String fieldName, final Object value) throws Throwable {

    }

    public void queryForAll() throws Throwable {

    }

    public void deleteWhere(final String name, final String value) throws Throwable {

    }
    // endregion

}
