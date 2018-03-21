package com.example.paralect.easytime.main.projects.project.invoice;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.CollectionUtil;
import com.example.paralect.easytime.utils.TextUtil;
import com.example.paralect.easytime.model.ExpenseUnit;
import com.example.paralect.easytime.utils.ExpenseUtil;
import com.example.paralect.easytime.model.Expense;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.paralect.easytime.model.ExpenseUnit.Type.DRIVING;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.MATERIAL;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.OTHER;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.TIME;

/**
 * Created by Oleg Tarashkevich on 31.01.2018.
 */

class ProjectInvoicePresenter extends SearchViewPresenter<List<InvoiceCell>> {

    private Job mJob;

    SearchViewPresenter<List<InvoiceCell>> setJob(Job job) {
        mJob = job;
        return this;
    }

    @Override
    public IDataPresenter<List<InvoiceCell>, String[]> requestData(final String[] parameters) {
        Observable<List<InvoiceCell>> observable = Observable.create(new ObservableOnSubscribe<List<InvoiceCell>>() {
            @Override
            public void subscribe(ObservableEmitter<List<InvoiceCell>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        List<InvoiceCell> invoices = getInvoices(mJob.getId());
                        emitter.onNext(invoices);
                        emitter.onComplete();
                    }

                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<InvoiceCell>>() {
                    @Override
                    public void onNext(List<InvoiceCell> expenses) {
                        if (mView != null)
                            mView.onDataReceived(expenses);
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

    @Override
    protected void setTitle() {
        // no need
    }


    List<InvoiceCell> getInvoices(String jobId) {

        List<InvoiceCell> cells = new ArrayList<>();
        List<Expense> consumables = EasyTimeManager.getInstance().getAllExpenses(jobId);

        // Time
        List<InvoiceCell> timeCells = getMergedExpenseResult(consumables, TIME);
        if (CollectionUtil.isNotEmpty(timeCells)) {
            Cell header = new Cell();
            header.setName("TIME");
            header.setType(InvoiceCell.Type.HEADER);

            cells.add(header);
            cells.addAll(timeCells);
        }

        // Expenses
        List<InvoiceCell> drivingCells = getMergedExpenseResult(consumables, DRIVING);
        List<InvoiceCell> othersCells = getMergedExpenseResult(consumables, OTHER);

        if (CollectionUtil.isNotEmpty(drivingCells) || CollectionUtil.isNotEmpty(othersCells)) {
            Cell header = Cell.createHeader("EXPENSES");
            cells.add(header);

            cells.addAll(drivingCells);
            cells.addAll(othersCells);
        }

        // Materials
        List<InvoiceCell> materialCells = getMergedExpenseResult(consumables, MATERIAL);
        if (CollectionUtil.isNotEmpty(materialCells)) {
            Cell header = Cell.createHeader("MATERIALS");

            cells.add(header);
            cells.addAll(materialCells);
        }

        return cells;
    }

    private List<InvoiceCell> getMergedExpenseResult(List<Expense> expenses, final @ExpenseUnit.Type String expenseType) {

        if (CollectionUtil.isEmpty(expenses))
            return Collections.emptyList();

        final long[] totalValue = {0};
        List<InvoiceCell> filtered =
                Observable.fromIterable(expenses)
                        // Filter expenses by type
                        .filter(new Predicate<Expense>() {
                            @Override
                            public boolean test(Expense expense) throws Exception {
                                String type = expense.getType();
                                return TextUtil.isNotEmpty(type) && type.equalsIgnoreCase(expenseType);
                            }
                        })
                        // Convert to map. Name is the key
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
                        // Count total values
                        .map(new Function<Collection<Expense>, Expense>() {
                            @Override
                            public Expense apply(Collection<Expense> expenses) throws Exception {

                                Expense newExpense = null;

                                if (CollectionUtil.isNotEmpty(expenses)) {
                                    long value = 0;
                                    Expense lastExpense = null;
                                    for (Expense expense : expenses) {
                                        value += expense.getValue();
                                        lastExpense = expense;
                                        
                                    }

                                    totalValue[0] += value;

                                    final Expense finalLastExpense = lastExpense;
                                    newExpense = Expense.copy(finalLastExpense);
                                    newExpense.setValue(value);
                                    newExpense.setValueWithUnitName(new Expense.ExpenseValueWithUnit(value){
                                        @Override
                                        public String getMaterialUnit() {
                                            return finalLastExpense.getValueWithUnitName();
                                        }
                                    });

                                }
                                return newExpense;
                            }
                        })
                        .toList()
                        // Add total cell if type is not DRIVING and not MATERIAL
                        .map(new Function<List<Expense>, List<InvoiceCell>>() {
                            @Override
                            public List<InvoiceCell> apply(List<Expense> expenses) throws Exception {
                                List<InvoiceCell> cells = new ArrayList<InvoiceCell>(expenses);
                                if (CollectionUtil.isNotEmpty(cells) && !expenseType.equalsIgnoreCase(DRIVING)
                                        && !expenseType.equalsIgnoreCase(MATERIAL)) {

                                    String value = ExpenseUtil.getUnit(expenseType, new Expense.ExpenseValueWithUnit(totalValue[0]));
                                    Cell total = Cell.createTotal(value);
                                    cells.add(total);
                                }

                                return cells;
                            }
                        })
                        .blockingGet();

        return filtered;
    }

}
