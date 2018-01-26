package com.example.paralect.easytime.main.projects.project.details;

import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.utils.TextUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 16.01.2018.
 */

public class ProjectExpensesPresenter extends SearchViewPresenter<List<Expense>> {

    @Override
    public ProjectExpensesPresenter requestData(final String[] parameters) {
        Observable<List<Expense>> observable = Observable.create(new ObservableOnSubscribe<List<Expense>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Expense>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        final String jobId = parameters[0];
                        List<Expense> consumables = EasyTimeManager.getInstance().getAllExpenses(jobId);

                        // Time
                        List<Expense> filteredByTime = Observable.fromIterable(consumables)
                                .filter(new Predicate<Expense>() {
                                    @Override
                                    public boolean test(Expense expense) throws Exception {
                                        String type = expense.getType();
                                        return TextUtil.isNotEmpty(type) && type.equalsIgnoreCase(Expense.Type.TIME);
                                    }
                                })
                                .toList()
                                .blockingGet();

                        // Material
                        List<Expense> filteredByMaterial = Observable.fromIterable(consumables)
                                .filter(new Predicate<Expense>() {
                                    @Override
                                    public boolean test(Expense expense) throws Exception {
                                        String type = expense.getType();
                                        return TextUtil.isNotEmpty(type) && type.equalsIgnoreCase(Expense.Type.MATERIAL);
                                    }
                                })
                                .toList()
                                .blockingGet();

                        // Driving
                        List<Expense> filteredByDriving = Observable.fromIterable(consumables)
                                .filter(new Predicate<Expense>() {
                                    @Override
                                    public boolean test(Expense expense) throws Exception {
                                        String type = expense.getType();
                                        return TextUtil.isNotEmpty(type) && type.equalsIgnoreCase(Expense.Type.DRIVING);
                                    }
                                })
                                .toList()
                                .blockingGet();

                        // Other
                        List<Expense> filteredByOther = Observable.fromIterable(consumables)
                                .filter(new Predicate<Expense>() {
                                    @Override
                                    public boolean test(Expense expense) throws Exception {
                                        String type = expense.getType();
                                        return TextUtil.isNotEmpty(type) && type.equalsIgnoreCase(Expense.Type.OTHER);
                                    }
                                })
                                .toList()
                                .blockingGet();

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
                .subscribe(new DisposableObserver<List<Expense>>() {
                    @Override
                    public void onNext(List<Expense> consumables) {
                        if (mView != null)
                            mView.onDataReceived(consumables);
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
