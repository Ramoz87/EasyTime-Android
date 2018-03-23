package com.example.paralect.easytime.manager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.paralect.easytime.model.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.DataSource;
import com.paralect.datasource.core.Entity;
import com.paralect.datasource.core.EntityConverter;
import com.paralect.datasource.rx.DataSourceRx;

import java.sql.SQLException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class ORMLiteDataSource extends DatabaseHelper implements
        DataSource<QueryBuilder<?, ?>>,
        DataSourceRx<QueryBuilder<?, ?>> {

    public ORMLiteDataSource(@NonNull Context context) {
        super(context);
    }

    // region Non synchronous
    @Override
    public <M extends Entity> M get(Class<M> type, QueryBuilder<?, ?> parameter) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        QueryBuilder<M, ?> param = (QueryBuilder<M, ?>) parameter;
        return dao.query(param.prepare()).get(0);
    }

    @Override
    public <M extends Entity> List<M> getList(Class<M> type, QueryBuilder<?, ?> parameter) throws SQLException {
        QueryBuilder<M, ?> param = (QueryBuilder<M, ?>) parameter;
        return param.query();
    }

    public <M extends Entity> void save(Class<M> type, M model) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        dao.createOrUpdate(model);
    }

    @Override
    public <M extends Entity> void update(Class<M> type, M model) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        dao.update(model);
    }

    public <M extends Entity> void delete(Class<M> type, M model) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        dao.delete(model);
    }
    // endregion


    public <IN, EX, C extends EntityConverter<IN, EX, QueryBuilder<IN, ?>>> IN get(C converter) throws SQLException {
        Dao<IN, ?> dao = getDao(converter.getClazz());
        QueryBuilder<IN, ?> param = converter.getParameter();
        IN entity = dao.query(param.prepare()).get(0);
        return (IN) converter.unwrap(entity);
    }

    // region Synchronous
    @Override
    public <M extends Entity> Single<M> getAsync(final Class<M> type, final QueryBuilder<?, ?> parameter) {
        return Single.create(new SingleOnSubscribe<M>() {
            @Override
            public void subscribe(SingleEmitter<M> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        M model = get(type, parameter);
                        emitter.onSuccess(model);
                    }
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }
            }
        });
    }

    @Override
    public <M extends Entity> Single<List<M>> getListAsync(final Class<M> type, final QueryBuilder<?, ?> parameter) {
        return Single.create(new SingleOnSubscribe<List<M>>() {
            @Override
            public void subscribe(SingleEmitter<List<M>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        List<M> list = getList(type, parameter);
                        emitter.onSuccess(list);
                    }
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }
            }
        });
    }

    @Override
    public <M extends Entity> Single<Object> saveAsync(final Class<M> type, final M model) {
        return Single.create(new SingleOnSubscribe<Object>() {
            @Override
            public void subscribe(SingleEmitter<Object> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        save(type, model);
                        emitter.onSuccess(NOTHING);
                    }
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }
            }
        });
    }

    @Override
    public <M extends Entity> Single<Object> updateAsync(final Class<M> type, final M model) {
        return Single.create(new SingleOnSubscribe<Object>() {
            @Override
            public void subscribe(SingleEmitter<Object> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        update(type, model);
                        emitter.onSuccess(NOTHING);
                    }
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }
            }
        });
    }

    @Override
    public <M extends Entity> Single<Object> deleteAsync(final Class<M> type, final M model) {
        return Single.create(new SingleOnSubscribe<Object>() {
            @Override
            public void subscribe(SingleEmitter<Object> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        delete(type, model);
                        emitter.onSuccess(NOTHING);
                    }
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }
            }
        });
    }
    // endregion
}
