package com.paralect.datasource.core;

/**
 * Created by Oleg Tarashkevich on 23/03/2018.
 */

public abstract class EntityRequestImpl<IN, EX, P> implements EntityRequest<IN, EX, P> {

    private P mParameter;
    private IN mEntity;

    @Override
    public void setParameter(P parameter) {
        mParameter = parameter;
    }

    @Override
    public P getParameter() {
        return mParameter;
    }

    @Override
    public void setEntity(IN internalEntity) {
        mEntity = internalEntity;
    }

    @Override
    public IN getEntity() {
        return mEntity;
    }

}
