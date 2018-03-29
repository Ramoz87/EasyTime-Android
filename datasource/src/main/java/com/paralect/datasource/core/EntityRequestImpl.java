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
}
