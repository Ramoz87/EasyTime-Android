package com.paralect.easytimedataormlite.request;

import com.paralect.datasource.core.EntityRequest;

/**
 * Created by Oleg Tarashkevich on 23/03/2018.
 */

public abstract class BaseRequest<IN, EX, P> implements EntityRequest<IN, EX, P> {

    private P mParameter;
    private IN mInternalEntity;
    private EX mExternalEntity;

    @Override
    public void setParameter(P parameter) {
        mParameter = parameter;
    }

    @Override
    public P getParameter() {
        return mParameter;
    }

    @Override
    public void setInternalEntity(IN internalEntity) {
        mInternalEntity = internalEntity;
    }

    @Override
    public IN getInternalEntity() {
        return mInternalEntity;
    }

    @Override
    public void setExternalEntity(EX externalEntity) {
        mExternalEntity = externalEntity;
    }

    @Override
    public EX getExternalEntity() {
        return mExternalEntity;
    }

}
