package com.example.paralect.easytime.main.projects.project;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Consumable;
import com.example.paralect.easytime.model.Job;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 18.01.2018.
 */

public class ActivityPresenter extends SearchViewPresenter<List<Consumable>> {

    @Override
    public IDataPresenter<List<Consumable>, String[]> requestData(final String[] parameters) {
        Observable<List<Consumable>> observable = Observable.create(new ObservableOnSubscribe<List<Consumable>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Consumable>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        final String jobId = parameters[0];
                        List<Consumable> consumables = EasyTimeManager.getInstance().getConsumables(jobId);
                        emitter.onNext(consumables);
                        emitter.onComplete();
                    }

                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Consumable>>() {
                    @Override
                    public void onNext(List<Consumable> con) {
                        if (mView != null)
                            mView.onDataReceived(con);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return this;
    }
}
