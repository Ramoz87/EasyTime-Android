package com.example.paralect.easytime.main.projects.project;

import android.text.SpannableString;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.CalendarUtils;

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

public class ActivityPresenter extends SearchViewPresenter<List<Expense>> {

    private Job mJob;

    @Override
    public IDataPresenter<List<Expense>, String[]> requestData(final String[] parameters) {
        Observable<List<Expense>> observable = Observable.create(new ObservableOnSubscribe<List<Expense>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Expense>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        final String date = parameters[1];
                        List<Expense> expenses = getExpenses(mJob.getJobId(), date);
                        emitter.onNext(expenses);
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
                    public void onNext(List<Expense> expenses) {
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

    public SearchViewPresenter<List<Expense>> setJob(Job job) {
        mJob = job;
        return this;
    }

    protected List<Expense> getExpenses(String jobId, String date){
        return EasyTimeManager.getInstance().getAllExpenses(jobId, date);
    }

    @Override
    protected void setTitle() {
        SpannableString spannableDateString = CalendarUtils.getSpannableDateString(EasyTimeApplication.getContext(), mCalendar, R.color.blue);
        mTextView.setText(spannableDateString);
    }

}
