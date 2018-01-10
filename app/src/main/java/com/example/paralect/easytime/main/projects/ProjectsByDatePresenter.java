package com.example.paralect.easytime.main.projects;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.main.search.ISearchByDateViewPresenter;
import com.example.paralect.easytime.main.search.SearchByDateViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Job;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 10.01.2018.
 */

public class ProjectsByDatePresenter extends SearchByDateViewPresenter<List<Job>> {

    public ProjectsByDatePresenter(int year, int month, int day) {
        super(year, month, day);
    }

    @Override
    public ISearchByDateViewPresenter<List<Job>> requestData(int year, int month, int day) {
        Observable<List<Job>> observable = Observable.create(new ObservableOnSubscribe<List<Job>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Job>> emitter) throws Exception {
                try {

                    if (!emitter.isDisposed()) {
                        List<Job> jobs = EasyTimeManager.getJobs(EasyTimeApplication.getContext(), "");
                        emitter.onNext(jobs);
                        emitter.onComplete();
                    }

                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Job>>() {
                    @Override
                    public void onNext(List<Job> jobs) {
                        if (mView != null)
                            mView.onDataReceived(jobs);
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
