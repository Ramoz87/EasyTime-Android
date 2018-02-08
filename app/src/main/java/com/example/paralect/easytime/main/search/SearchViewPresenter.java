package com.example.paralect.easytime.main.search;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.utils.CalendarUtils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

/**
 * This class helps to retrieve data with a delay and asynchronous
 */
public abstract class SearchViewPresenter<DATA> implements ISearchViewPresenter<DATA, String[]>, DatePickerDialog.OnDateSetListener {
    private static final String TAG = SearchViewPresenter.class.getSimpleName();

    private PublishProcessor<String[]> mPublisher;
    protected IDataView<DATA> mView;

    private String query;
    private String date;

    protected TextView mTextView;
    protected Calendar mCalendar = Calendar.getInstance();

    private void setupPublisher() {
        if (mPublisher == null) {
            mPublisher = PublishProcessor.create();
            final Flowable<String[]> flowable = mPublisher.onBackpressureBuffer();
            int delay = 200;
            flowable.debounce(delay, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String[]>() {
                        @Override
                        public void accept(String[] queries) throws Exception {
                            requestData(queries);
                        }
                    });
        }
    }

    @Override
    public ISearchViewPresenter<DATA, String[]> setupQuerySearch(SearchView searchView) {
        setupPublisher();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (date == null)
                    date = getDate();
                if (mPublisher != null)
                    mPublisher.onNext(new String[]{query, date});
                return true;
            }
        });
        return this;
    }

    @Override
    public ISearchViewPresenter<DATA, String[]> setupDateSearch(TextView view) {
        setupPublisher();
        mTextView = view;
        setTitle();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = createDatePickerDialog(view.getContext());
                datePickerDialog.show();
            }
        });
        return this;
    }

    protected DatePickerDialog createDatePickerDialog(Context context) {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(context, this, year, month, day);
    }

    @Override
    public SearchViewPresenter<DATA> setDataView(IDataView<DATA> view) {
        this.mView = view;
        return this;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        mCalendar = calendar;

        setTitle();

        date = getDate();
        Log.d(TAG, "on date set: " + date);

        if (mPublisher != null) {
            mPublisher.onNext(new String[]{query, date});
        }
    }

    protected void setTitle(){
        SpannableString spannableDateString = CalendarUtils.getSpannableDateString(EasyTimeApplication.getContext(), mCalendar);
        mTextView.setText(spannableDateString);
    }

    public SpannableString getSpannableDateString(){
       return CalendarUtils.getSpannableDateString(EasyTimeApplication.getContext(), mCalendar);
    }

    public String getDate(){
        return CalendarUtils.stringFromDate(mCalendar.getTime(), CalendarUtils.SHORT_DATE_FORMAT);
    }
}
