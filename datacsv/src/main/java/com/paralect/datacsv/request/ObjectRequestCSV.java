package com.paralect.datacsv.request;

import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Type;
import com.paralect.datasource.core.EntityRequestImpl;

/**
 * Created by Oleg Tarashkevich on 05/04/2018.
 */

public class ObjectRequestCSV extends JobRequestCSV<Object> {

    public ObjectRequestCSV() {
        setQuery("db/types.csv");
    }

    @Override
    public Object toAppEntity(String[] fields) {
        Object object = new Object();
        fillJob(object, fields);

        Address address = new Address();
        address.setStreet(fields[16]);
        address.setCity(fields[17]);
        address.setZip(fields[18]);
        object.setAddress(address);
        return object;
    }

    @Override
    public String[] toDataSourceEntity(Object type) {
        return new String[0];
    }

    @Override
    public Class<String[]> getDataSourceEntityClazz() {
        return String[].class;
    }

    @Override
    public Class<Object> getAppEntityClazz() {
        return Object.class;
    }

}
