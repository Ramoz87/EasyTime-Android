package com.example.paralect.easytime.main.projects.project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.SpannableString;
import android.util.Pair;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.RxBus;
import com.example.paralect.easytime.utils.TextUtil;
import com.paralect.expensesormlite.Expense;

import java.util.Calendar;
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

public class ActivityPresenter extends SearchViewPresenter<Pair<Integer, List<Expense>>> {

    private Job mJob;

    @Override
    public IDataPresenter<Pair<Integer, List<Expense>>, String[]> requestData(final String[] parameters) {
        Observable<Pair<Integer, List<Expense>>> observable = Observable.create(new ObservableOnSubscribe<Pair<Integer, List<Expense>>>() {
            @Override
            public void subscribe(ObservableEmitter<Pair<Integer, List<Expense>>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        final String date = parameters[1];
                        List<Expense> expenses = getExpenses(mJob.getJobId(), date);

                        int count = (int) EasyTimeManager.getInstance().getTotalExpensesCount(mJob.getJobId());
                        emitter.onNext(new Pair<>(count, expenses));
                        emitter.onComplete();
                    }

                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Pair<Integer, List<Expense>>>() {
                    @Override
                    public void onNext(Pair<Integer, List<Expense>> expenses) {
                        if (mView != null)
                            mView.onDataReceived(expenses);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return this;
    }

    public SearchViewPresenter<Pair<Integer, List<Expense>>> setJob(Job job) {
        mJob = job;
        return this;
    }

    protected List<Expense> getExpenses(String jobId, String date){
        // send date to InformationFragment
        if (TextUtil.isNotEmpty(date))
            RxBus.getInstance().send(date);
        return EasyTimeManager.getInstance().getAllExpenses(jobId, date);
    }

    @Override
    protected void setTitle() {
        SpannableString spannableDateString = CalendarUtils.getSpannableDateString(EasyTimeApplication.getContext(), mCalendar, R.color.blue);
        mTextView.setText(spannableDateString);
    }

    // prevent date pciker dialog choose the future
    @Override
    protected DatePickerDialog createDatePickerDialog(Context context) {
        Calendar maxDate = Calendar.getInstance();
        CalendarUtils.setCalendarToMax(maxDate);
        DatePickerDialog dpd = super.createDatePickerDialog(context);
        dpd.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
        return dpd;
    }

}
