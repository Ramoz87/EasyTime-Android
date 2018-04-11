package com.paralect.datacsv.request;

import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Object;

/**
 * Created by Oleg Tarashkevich on 05/04/2018.
 */

public class ObjectRequestCSV extends JobRequestCSV<Object> {

    public ObjectRequestCSV() {
        setQuery("db/objects.csv");
    }

    @Override
    public Object toAppEntity(String[] fields) {
        Object object = null;
        try {
            object = new Object();
            fillJob(object, fields);

            Address address = new Address();
            address.setStreet(fields[16]);
            address.setCity(fields[17]);
            address.setZip(fields[18]);
            object.setAddress(address);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public Class<Object> getAppEntityClazz() {
        return Object.class;
    }

}
