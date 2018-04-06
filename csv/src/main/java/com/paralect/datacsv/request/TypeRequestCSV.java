package com.paralect.datacsv.request;

import com.example.paralect.easytime.model.Type;
import com.paralect.datasource.core.EntityRequestImpl;

import java.io.File;

/**
 * Created by Oleg Tarashkevich on 05/04/2018.
 */

public class TypeRequestCSV extends EntityRequestImpl<String[], Type, File> {

    public TypeRequestCSV() {
        setQuery("db/types.csv");
    }

    @Override
    public Type toAppEntity(String[] fields) {
        Type type = new Type();
        type.setTypeId(fields[0]);
        type.setType(fields[1]);
        type.setName(fields[2]);
        return type;
    }

    @Override
    public String[] toDataSourceEntity(Type type) {
        return new String[0];
    }

    @Override
    public Class<String[]> getDataSourceEntityClazz() {
        return String[].class;
    }

    @Override
    public Class<Type> getAppEntityClazz() {
        return Type.class;
    }

}
