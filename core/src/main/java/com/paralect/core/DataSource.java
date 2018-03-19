package com.paralect.core;

import android.util.SparseArray;

import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

/**
 * @param <M> type of model
 * @param <P> type of parameter for query purposes
 * @param <E> type of throwable (exception or error)
 */
public class DataSource<M extends Model, P, E extends Throwable> {

    // region Get
    public M getModelById(long id) throws E {
        return null;
    }

    public M getModel(P parameter) throws E {
        return null;
    }

    public List<M> getModels(P parameter) throws E {
        return null;
    }

    public long getCount(P parameter) throws E {
        return -1;
    }
    // endregion

    // region Save
    public void saveModel(M model) throws E {
    }

    public void saveModels(List<M> models) throws E {
    }

    public M saveAndGetModel(M model, P parameter) throws E {
        // save
        saveModel(model);
        // retrieve
        return getModel(parameter);
    }
    // endregion

    public long deleteModel(M model) throws E {
        return -1;
    }

    public long deleteModel(P parameter) throws E {
        return -1;
    }

    public void deleteModels(List<M> models) throws E {
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
