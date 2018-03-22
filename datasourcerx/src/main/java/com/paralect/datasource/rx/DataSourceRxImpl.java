package com.paralect.datasource.rx;

import com.paralect.datasource.core.DataSource;
import com.paralect.datasource.core.Entity;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by Oleg Tarashkevich on 21/03/2018.
 */

public abstract class DataSourceRxImpl<P> implements DataSource<P>, DataSourceRx<P>{

    // region Synchronous
    @Override
    public <M extends Entity> Single<M> getAsync(final Class<M> type, final P parameter) {
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
    public <M extends Entity> Single<List<M>> getListAsync(final Class<M> type, final P parameter) {
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
