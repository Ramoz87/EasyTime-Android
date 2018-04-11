package com.paralect.datacsv.request;

import com.paralect.datasource.core.EntityRequest;

import java.io.File;

/**
 * Created by Oleg Tarashkevich on 06/04/2018.
 */

public abstract class CSVRequest<AP> extends EntityRequest<String[], AP, File> {

    @Override
    public String[] toDataSourceEntity(AP appEntity) {
        return new String[0];
    }

    @Override
    public Class<String[]> getDataSourceEntityClazz() {
        return String[].class;
    }

}
