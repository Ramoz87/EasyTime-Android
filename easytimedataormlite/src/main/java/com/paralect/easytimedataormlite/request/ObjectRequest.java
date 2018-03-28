package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Object;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.EntityRequestImpl;
import com.paralect.easytimedataormlite.model.ObjectEntity;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class ObjectRequest extends BaseJobRequest<Object, ObjectEntity> {

    @Override
    public Object toInternalEntity(ObjectEntity ex) {
        Object in = new Object();
        if (ex != null) {
            populateInternalEntity(in, ex);

            in.setAddressString(ex.getAddressString());
            in.setCityString(ex.getCityString());
            in.setZipString(ex.getZipString());

            in.setDateEnd(ex.getDateEnd());
            in.setDateStart(ex.getDateStart());
            in.setProjectId(ex.getProjectId());
        }
        return in;
    }

    @Override
    public ObjectEntity toExternalEntity(Object in) {
        ObjectEntity ex = new ObjectEntity();
        if (in != null) {
            populateExternalEntity(in, ex);

            ex.setAddressString(in.getAddressString());
            ex.setCityString(in.getCityString());
            ex.setZipString(in.getZipString());

            ex.setDateEnd(in.getDateEnd());
            ex.setDateStart(in.getDateStart());
            ex.setProjectId(in.getProjectId());
        }
        return ex;
    }

    @Override
    public Class<Object> getInnerEntityClazz() {
        return Object.class;
    }

    @Override
    public Class<ObjectEntity> getExternalEntityClazz() {
        return ObjectEntity.class;
    }
}
