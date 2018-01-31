package com.example.paralect.easytime.main.projects.project.invoice;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.CollectionUtils;
import com.example.paralect.easytime.utils.TextUtil;

import java.util.ArrayList;
import java.util.Collection;
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
                        final String date = parameters[1];
                        List<InvoiceCell> invoices = getInvoices(mJob.getJobId(), date);
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


    List<InvoiceCell> getInvoices(String jobId, String date) {

        List<InvoiceCell> cells = new ArrayList<>();
        List<Expense> consumables = EasyTimeManager.getInstance().getAllExpenses(jobId, date);

        // Time
        List<InvoiceCell> timeCells = getMergedExpenseResult(consumables, Expense.Type.TIME);
        if (CollectionUtils.isNotEmpty(timeCells)) {
            Cell header = new Cell();
            header.setName("TIME");
            header.setType(InvoiceCell.Type.HEADER);

            cells.add(header);
            cells.addAll(timeCells);
        }

        // Expenses
        List<InvoiceCell> drivingCells = getMergedExpenseResult(consumables, Expense.Type.DRIVING);
        List<InvoiceCell> othersCells = getMergedExpenseResult(consumables, Expense.Type.OTHER);

        if (CollectionUtils.isNotEmpty(drivingCells) || CollectionUtils.isNotEmpty(othersCells)) {
            Cell header = Cell.createHeader("EXPENSES");
            cells.add(header);

            cells.addAll(drivingCells);
            cells.addAll(othersCells);
        }

        // Materials
        List<InvoiceCell> materialCells = getMergedExpenseResult(consumables, Expense.Type.MATERIAL);
        if (CollectionUtils.isNotEmpty(materialCells)) {
            Cell header = Cell.createHeader("MATERIALS");

            cells.add(header);
            cells.addAll(materialCells);
        }

//        List<Expense> driving = getMergedExpenseResult(consumables, Expense.Type.DRIVING);
//        List<Expense> other = getMergedExpenseResult(consumables, Expense.Type.OTHER);


//            totalPrice.setText(res.getString(R.string.expense_price, value));


        return cells;
    }

    private List<InvoiceCell> getMergedExpenseResult(List<Expense> expenses, final @Expense.Type String expenseType) {
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

                                long value = 0;
                                Expense lastExpense = null;
                                for (Expense expense : expenses) {
                                    value += expense.getValue();
                                    lastExpense = expense;
                                }

                                totalValue[0] += value;

                                Expense newExpense = Expense.reCreate(lastExpense);
                                newExpense.setValue(value);

                                return newExpense;
                            }
                        })
                        .toList()
                        // Add total cell if type is not DRIVING
                        .map(new Function<List<Expense>, List<InvoiceCell>>() {
                            @Override
                            public List<InvoiceCell> apply(List<Expense> expenses) throws Exception {
                                List<InvoiceCell> cells = new ArrayList<InvoiceCell>(expenses);
                                if (!expenseType.equalsIgnoreCase(Expense.Type.DRIVING)) {
                                    String value = Expense.getTypedValue(expenseType, totalValue[0]);
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
