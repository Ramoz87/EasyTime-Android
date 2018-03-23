package com.example.paralect.easytime.manager;

import android.util.SparseArray;

import com.paralect.datasource.core.DataSource;

/**
 * Created by Oleg Tarashkevich on 20/03/2018.
 */

public abstract class Factory<DATA_SOURCE extends DataSource> {

    protected final SparseArray<DATA_SOURCE> dataSources = new SparseArray<>();

    /**
     * Populates a {@link DataSource} instance.
     */

    public Factory<DATA_SOURCE> init() {
        int count = getDataSourceCount();
        for (int i = 0; i < count; i++) {
            int dataSourceType = getDataSourceType(i);
            DATA_SOURCE dataSource = onCreateDataSource(dataSourceType);
            dataSources.put(dataSourceType, dataSource);
        }
        return this;
    }

    /**
     * Get a {@link DataSource} instance.
     */
    public DATA_SOURCE getDataSource(int dataSourceType) {
        return dataSources.get(dataSourceType);
    }

    /**
     * Creates a {@link DataSource} instance.
     */
    public abstract DATA_SOURCE onCreateDataSource(int dataSourceType);


    /**
     * Count of {@link DataSource} elements.
     */
    public abstract int getDataSourceCount();

    /**
     * Get a type of {@link DataSource} by position.
     */
    public abstract int getDataSourceType(int position);
}