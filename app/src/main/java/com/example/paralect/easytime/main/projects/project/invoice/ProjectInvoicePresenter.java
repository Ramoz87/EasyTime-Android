package com.example.paralect.easytime.main.projects.project.invoice;

import com.example.paralect.easytime.main.projects.project.ActivityPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by alexei on 16.01.2018.
 */

class ProjectInvoicePresenter extends ActivityPresenter {

    @Override
    protected void setTitle() {
      // empty
    }

    @Override
    protected List<Expense> getExpenses(String jobId, String date) {
        List<Expense> consumables = EasyTimeManager.getInstance().getAllExpenses(jobId, date);
        List<Expense> list = getMergedExpenseResult(consumables, Expense.Type.TIME);
        return list;
    }

    private List<Expense> getMergedExpenseResult(List<Expense> expenses, final @Expense.Type String expenseType) {
        List<Expense> filtered =
                Observable.fromIterable(expenses)
//                        .filter(new Predicate<Expense>() {
//                            @Override
//                            public boolean test(Expense expense) throws Exception {
//                                String type = expense.getType();
//                                return TextUtil.isNotEmpty(type) && type.equalsIgnoreCase(expenseType);
//                            }
//                        })
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
