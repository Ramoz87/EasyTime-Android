package com.example.paralect.easytime.main.projects.project.details;

import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.utils.CollectionUtils;
import com.example.paralect.easytime.utils.TextUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

                        List<Expense> list = getMergedExpenseResult(consumables, Expense.Type.TIME);

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

    private List<Expense> getMergedExpenseResult(List<Expense> expenses, final @Expense.Type String queryType) {
        List<Expense> filtered =
                Observable.fromIterable(expenses)
                        .filter(new Predicate<Expense>() {
                            @Override
                            public boolean test(Expense expense) throws Exception {
                                String type = expense.getType();
                                return TextUtil.isNotEmpty(type) && type.equalsIgnoreCase(queryType);
                            }
                        })
                        .toMultimap(new Function<Expense, String>() {
                            @Override
                            public String apply(Expense expense) throws Exception {
                                return expense.getName();
                            }
                        })

                        .flattenAsObservable(new Function<Map<String, Collection<Expense>>, Iterable<Collection<Expense>>>() {
                            @Override
                            public Iterable<Collection<Expense>> apply(Map<String, Collection<Expense>> stringCollectionMap) throws Exception {
                                return stringCollectionMap.values();
                            }
                        })

                        .map(new Function<Collection<Expense>, Expense>() {
                            @Override
                            public Expense apply(Collection<Expense> expenses) throws Exception {

                                Expense newExpense = new Expense();

                                long value = 0;
                                for (Expense expense : expenses){
                                    value += expense.getValue();
                                    newExpense = Expense.reCreate(expense);
                                }

                                newExpense.setValue(value);

                                return newExpense;
                            }
                        })

//                        // todo working
//                        .map(new Function<Map<String, Collection<Expense>>, Collection<Collection<Expense>>>() {
//                            @Override
//                            public Collection<Collection<Expense>> apply(Map<String, Collection<Expense>> stringCollectionMap) throws Exception {
//                                return stringCollectionMap.values();
//                            }
//                        })


//                .flatMapObservable(new Function<Map<String, Collection<Expense>>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(Map<String, Collection<Expense>> stringCollectionMap) throws Exception {
//                        return stringCollectionMap.entrySet();
//                    }
//                })

//                        .map(new Function<Map<String, Collection<Expense>>, Set<Map.Entry<String, Collection<Expense>>>>() {
//
//                            @Override
//                            public Set<Map.Entry<String, Collection<Expense>>> apply(Map<String, Collection<Expense>> stringCollectionMap) throws Exception {
//                                return stringCollectionMap.entrySet().toArray();
//                            }
//                        })

//                        .flatMapIterable(new Function<Set<Map.Entry<String, Collection<Expense>>>, Iterable<Expense>>() {
//                            @Override
//                            public Iterable<Expense> apply(Set<Map.Entry<String, Collection<Expense>>> entries) throws Exception {
//                                return entries.iterator();
//                            }
//                        })

//                        .flattenAsObservable(new Function<Set<Map.Entry<String, Collection<Expense>>>, Iterable<List<Expense>>>() {
//                            @Override
//                            public Iterable<List<Expense>> apply(Set<Map.Entry<String, Collection<Expense>>> entries) throws Exception {
////                                Iterable<List<Expense>> iterable = new ArrayList<>(entries);
//                                return entries.iterator();
//                            }
//                        })

//                        .map(new Function<List<Expense>, Expense>() {
//                            @Override
//                            public Expense apply(List<Expense> expenseList) throws Exception {
//                                Expense expense = new Expense();
//                                return expense;
//                            }
//                        })


                        .toList()
                        .blockingGet();

        return filtered;
    }

}
