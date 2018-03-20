package com.paralect.core;

import android.util.SparseArray;

import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

/**
 * @param <M> type of model
 * @param <P> type of parameter for query purposes
 */
public class DataSource<M extends Model, P> {

    public M getModelById(long id) throws Throwable {
        return null;
    }

    public M getModel(P parameter) throws Throwable {
        return null;
    }

    public List<M> getModels(P parameter) throws Throwable {
        return null;
    }

    public void saveModel(M model) throws Throwable {
    }

    public void deleteModel(M model) throws Throwable {
    }

    public static abstract class Factory<DATA_SOURCE extends DataSource> {

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
}
