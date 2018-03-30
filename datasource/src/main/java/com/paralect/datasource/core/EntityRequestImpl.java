package com.paralect.datasource.core;

/**
 * Created by Oleg Tarashkevich on 23/03/2018.
 */

public abstract class EntityRequestImpl<DS, AP, P> implements EntityRequest<DS, AP, P> {

    private P mParameter;
    private AP mEntity;

    @Override
    public void setParameter(P parameter) {
        mParameter = parameter;
    }

    @Override
    public P getParameter() {
        return mParameter;
    }

    @Override
    public void setEntity(AP dsEntity) {
        mEntity = dsEntity;
    }

    @Override
    public AP getEntity() {
        return mEntity;
    }


    // region General methods
    public <ID, H> void queryWhere(H helper, String name, ID value) throws Throwable {

    }

    public <H> void queryForId(H helper, String id) throws Throwable {

    }

    public <H> void queryForEqual(H helper, String id) throws Throwable {

    }

    public <H> void queryForLast(H helper, String orderByFieldName) throws Throwable {

    }

    public <H> void queryForFirst(H helper, String orderByFieldName) throws Throwable {

    }

    public <H> void queryForAll(H helper) throws Throwable {

    }

    public <H> void deleteWhere(H helper, String name, String value) throws Throwable {

    }
    // endregion

}
