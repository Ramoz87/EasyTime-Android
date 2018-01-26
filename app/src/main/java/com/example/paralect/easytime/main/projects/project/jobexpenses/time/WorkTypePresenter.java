package com.example.paralect.easytime.main.projects.project.jobexpenses.time;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.EasyTimeManager;
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

class WorkTypePresenter implements IDataPresenter<List<Type>, Void> {

    private IDataView<List<Type>> mIDataView;

    @Override
    public IDataPresenter<List<Type>, Void> setDataView(IDataView<List<Type>> view) {
        mIDataView = view;
        return this;
    }

    @Override
    public IDataPresenter<List<Type>, Void> requestData(Void parameter) {
        Flowable<List<Type>> flowable = Flowable.create(new FlowableOnSubscribe<List<Type>>() {
            @Override
            public void subscribe(FlowableEmitter<List<Type>> emitter) throws Exception {
                try {
                    if (!emitter.isCancelled()) {
                        List<Type> types = EasyTimeManager.getInstance().getTypes(WORK_TYPE);
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
                        if (mIDataView != null)
                            mIDataView.onDataReceived(data);
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
