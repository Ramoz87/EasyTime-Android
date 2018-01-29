package com.example.paralect.easytime.main.projects.project.jobexpenses.expenses;

import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 16.01.2018.
 */

class ExpensesPresenter extends SearchViewPresenter<ExpensesPresenter.ExpensesContainer> {

    private String mJobId;

    public ExpensesPresenter setJobId(String jobId) {
        mJobId = jobId;
        return this;
    }

    @Override
    public ExpensesPresenter requestData(final String[] parameters) {
        Observable<ExpensesPresenter.ExpensesContainer> observable = Observable.create(new ObservableOnSubscribe<ExpensesPresenter.ExpensesContainer>() {
            @Override
            public void subscribe(ObservableEmitter<ExpensesPresenter.ExpensesContainer> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {

                        final String searchQuery = parameters[0];

                        List<Expense> defaultExpenses = EasyTimeManager.getInstance().getDefaultExpenses(mJobId);
                        List<Expense> otherExpenses = EasyTimeManager.getInstance().getOtherExpenses(mJobId, searchQuery);

                        // Filter default expenses
                        defaultExpenses = Flowable.fromIterable(defaultExpenses)
                                .distinct(new Function<Expense, String>() {
                                    @Override
                                    public String apply(Expense expense) throws Exception {
                                        return expense.getName().toLowerCase();
                                    }
                                })
                                .filter(new Predicate<Expense>() {
                                    @Override
                                    public boolean test(Expense expense) throws Exception {
                                        return expense.getName().toLowerCase().contains(searchQuery.toLowerCase());
                                    }
                                })
                                .toList()
                                .blockingGet();

                        // Filter other expenses
                        otherExpenses = Flowable.fromIterable(otherExpenses)
                                .distinct(new Function<Expense, String>() {
                                    @Override
                                    public String apply(Expense expense) throws Exception {
                                        return expense.getName().toLowerCase();
                                    }
                                })
                                .toList()
                                .blockingGet();

                        final ExpensesContainer container = new ExpensesContainer();
                        container.defaultExpenses = defaultExpenses;
                        container.otherExpenses = otherExpenses;

                        emitter.onNext(container);
                        emitter.onComplete();
                    }

                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ExpensesPresenter.ExpensesContainer>() {
                    @Override
                    public void onNext(ExpensesPresenter.ExpensesContainer expenses) {
                        if (mView != null)
                            mView.onDataReceived(expenses);
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

    public class ExpensesContainer{

        private List<Expense> defaultExpenses;
        private List<Expense> otherExpenses;

        public List<Expense> getDefaultExpenses() {
            return defaultExpenses;
        }

        public List<Expense> getOtherExpenses() {
            return otherExpenses;
        }
    }
}
