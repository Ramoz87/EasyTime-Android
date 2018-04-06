package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Object;
import com.paralect.easytimedataormlite.model.ObjectEntity;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class ObjectRequestORM extends BaseJobRequestORM<ObjectEntity, Object> {

    @Override
    public Object toAppEntity(ObjectEntity ex) {
        if (ex == null) return null;

        Object in = new Object();
        populateInternalEntity(in, ex);

        in.setAddressString(ex.getAddressString());
        in.setCityString(ex.getCityString());
        in.setZipString(ex.getZipString());

        in.setDateEnd(ex.getDateEnd());
        in.setDateStart(ex.getDateStart());
        in.setProjectId(ex.getProjectId());
        return in;
    }

    @Override
    public ObjectEntity toDataSourceEntity(Object in) {
        if (in == null) return null;

        ObjectEntity ex = new ObjectEntity();
        populateExternalEntity(in, ex);

        ex.setAddressString(in.getAddressString());
        ex.setCityString(in.getCityString());
        ex.setZipString(in.getZipString());

        ex.setDateEnd(in.getDateEnd());
        ex.setDateStart(in.getDateStart());
        ex.setProjectId(in.getProjectId());
        return ex;
    }

    @Override
    public Class<ObjectEntity> getDataSourceEntityClazz() {
        return ObjectEntity.class;
    }

    @Override
    public Class<Object> getAppEntityClazz() {
        return Object.class;
    }
}
