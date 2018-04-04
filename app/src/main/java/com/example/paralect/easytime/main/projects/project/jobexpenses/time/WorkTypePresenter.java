package com.example.paralect.easytime.main.projects.project.jobexpenses.time;

import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.entitysource.TypeSource;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.utils.Logger;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.paralect.easytime.model.Type.TypeName.WORK_TYPE;

/**
 * Created by Oleg Tarashkevich on 26/01/2018.
 */

class WorkTypePresenter extends SearchViewPresenter<List<Type>> {

    private final TypeSource typeSource = new TypeSource();

    @Override
    public WorkTypePresenter requestData(final String[] parameters) {
        Flowable<List<Type>> flowable = Flowable.create(new FlowableOnSubscribe<List<Type>>() {
            @Override
            public void subscribe(FlowableEmitter<List<Type>> emitter) throws Exception {
                try {
                    if (!emitter.isCancelled()) {
                        List<Type> types = typeSource.getTypes(WORK_TYPE, parameters[0]);
                        emitter.onNext(types);
                        emitter.onComplete();
                    }

                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.LATEST);

        flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Type>>() {
                    @Override
                    public void accept(List<Type> data) {
                        if (mView != null)
                            mView.onDataReceived(data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable);
                    }
                });
        return this;
    }
}
