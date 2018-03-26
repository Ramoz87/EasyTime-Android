package com.paralect.datasource.rx;

import com.paralect.datasource.core.DataSource;
import com.paralect.datasource.core.Entity;
import com.paralect.datasource.core.EntityRequest;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by Oleg Tarashkevich on 21/03/2018.
 */

public abstract class DataSourceRxImpl<P> implements DataSource<P>, DataSourceRx<P>{

    // region Asynchronous
    @Override
    public <IN, EX> Single<IN> getAsync(final EntityRequest<IN, EX, P> request) {
        return Single.create(new SingleOnSubscribe<IN>() {
            @Override
            public void subscribe(SingleEmitter<IN> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        IN model = get(request);
                        emitter.onSuccess(model);
                    }
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }
            }
        });
    }

    @Override
    public <IN, EX> Single<List<IN>> getListAsync(final EntityRequest<IN, EX, P> request) {
        return Single.create(new SingleOnSubscribe<List<IN>>() {
            @Override
            public void subscribe(SingleEmitter<List<IN>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        List<IN> list = getList(request);
                        emitter.onSuccess(list);
                    }
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }
            }
        });
    }

    @Override
    public <IN, EX> Single<Object> saveAsync(final EntityRequest<IN, EX, P> request) {
        return Single.create(new SingleOnSubscribe<Object>() {
            @Override
            public void subscribe(SingleEmitter<Object> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        save(request);
                        emitter.onSuccess(NOTHING);
                    }
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }
            }
        });
    }

    @Override
    public <IN, EX> Single<Object> updateAsync(final EntityRequest<IN, EX, P> request) {
        return Single.create(new SingleOnSubscribe<Object>() {
            @Override
            public void subscribe(SingleEmitter<Object> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        update(request);
                        emitter.onSuccess(NOTHING);
                    }
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }
            }
        });
    }

    @Override
    public <IN, EX> Single<Object> deleteAsync(final EntityRequest<IN, EX, P> request) {
        return Single.create(new SingleOnSubscribe<Object>() {
            @Override
            public void subscribe(SingleEmitter<Object> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        delete(request);
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
