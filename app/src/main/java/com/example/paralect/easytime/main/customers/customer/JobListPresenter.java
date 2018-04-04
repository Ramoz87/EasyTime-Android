package com.example.paralect.easytime.main.customers.customer;

import android.util.Pair;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.entitysource.JobSource;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.ProjectType;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 23.01.2018.
 */

public class JobListPresenter implements IDataPresenter<List<Job>, Pair<Customer, Integer>> {

    private IDataView<List<Job>> view;
    private final JobSource jobSource = new JobSource();

    @Override
    public IDataPresenter<List<Job>, Pair<Customer, Integer>> setDataView(IDataView<List<Job>> view) {
        this.view = view;
        return this;
    }

    @Override
    public IDataPresenter<List<Job>, Pair<Customer, Integer>> requestData(final Pair<Customer, Integer> parameter) {
        Observable<List<Job>> observable = Observable.create(new ObservableOnSubscribe<List<Job>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Job>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        Customer customer = parameter.first;
                        int type = parameter.second;

                        List<Job> jobs = new ArrayList<>();
                        if (type == ProjectType.Type.TYPE_OBJECT)
                            jobs.addAll(jobSource.getObjects(customer));
                        else if (type == ProjectType.Type.TYPE_ORDER)
                            jobs.addAll(jobSource.getOrders(customer));
                        else if (type == ProjectType.Type.TYPE_PROJECT)
                            jobs.addAll(jobSource.getProjects(customer));

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
                    public void onNext(List<Job> materials) {
                        if (view != null)
                            view.onDataReceived(materials);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // throw new RuntimeException(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return this;
    }
}
