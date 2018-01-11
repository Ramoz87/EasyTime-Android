package com.example.paralect.easytime.main.projects;

import com.example.paralect.easytime.main.search.SearchViewPresenter;
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
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

public class ProjectsPresenter extends SearchViewPresenter<List<Job>> {

    @Override
    public ProjectsPresenter requestData(final String[] parameters) {
        Observable<List<Job>> observable = Observable.create(new ObservableOnSubscribe<List<Job>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Job>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        final String query = parameters[0];
                        final String date = parameters[1];
                        List<Job> jobs = EasyTimeManager.getInstance().getJobs(null, query, date);
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
