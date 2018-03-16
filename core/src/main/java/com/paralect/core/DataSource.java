package com.paralect.core;

import android.util.SparseArray;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public abstract class DataSource<MODEL extends Model> {

    public abstract void saveModel(MODEL model) throws SQLException;

    public abstract MODEL saveAndGetModel(MODEL model) throws SQLException;

    public abstract long deleteModel(MODEL model) throws SQLException;

    public abstract MODEL getModelById(long id) throws SQLException;

    public abstract List<MODEL> getModels() throws SQLException;

    public static abstract class Factory<DATA_SOURCE extends DataSource> {

        protected final SparseArray<DATA_SOURCE> dataSources = new SparseArray<>();

        /**
         * Populates a {@link DataSource} instance.
         */

        public Factory<DATA_SOURCE> init(){
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
}
