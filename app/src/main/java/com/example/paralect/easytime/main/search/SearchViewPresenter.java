package com.example.paralect.easytime.main.search;

import android.app.DatePickerDialog;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

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

public abstract class SearchViewPresenter<DATA> implements ISearchViewPresenter<DATA>, DatePickerDialog.OnDateSetListener {
    private static final String TAG = SearchViewPresenter.class.getSimpleName();

    private PublishProcessor<String[]> mPublisher;
    protected ISearchDataView<DATA> mView;

    private String query;
    private String date;

    private TextView mTextView;
    private Calendar calendar;

    public SearchViewPresenter(String startQuery, Calendar startCalendar) {
        setStartQuery(startQuery);
        setStartCalendar(startCalendar);
    }

    public SearchViewPresenter() {

    }

    public SearchViewPresenter<DATA> setStartQuery(String query) {
        this.query = query;
        return this;
    }

    public SearchViewPresenter<DATA> setStartCalendar(Calendar calendar) {
        this.calendar = calendar;

        if (calendar != null) {
            date = CalendarUtils.stringFromDate(calendar.getTime(), CalendarUtils.DEFAULT_DATE_FORMAT);
        }

        return this;
    }

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
                            requestData(queries[0], queries[1]);
                        }
                    });
        }
    }

    @Override
    public ISearchViewPresenter<DATA> setupQuerySearch(SearchView searchView) {
        setupPublisher();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (mPublisher != null)
                    mPublisher.onNext(new String[] {query, date});
                return true;
            }
        });
        return this;
    }

    @Override
    public ISearchViewPresenter<DATA> setupDateSearch(TextView view) {
        setupPublisher();
        this.mTextView = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), SearchViewPresenter.this, year, month, day);
                datePickerDialog.show();
            }
        });
        return this;
    }

    @Override
    public SearchViewPresenter<DATA> setSearchDataView(ISearchDataView<DATA> view) {
        this.mView = view;
        return this;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        this.calendar = calendar;
        SpannableString spannableDateString = CalendarUtils.getSpannableDateString(mTextView.getContext(), calendar);
        mTextView.setText(spannableDateString);

        date = CalendarUtils.stringFromDate(calendar.getTime(), CalendarUtils.DEFAULT_DATE_FORMAT);
        Log.d(TAG, "on date set: " + date);

        if (mPublisher != null) {
            mPublisher.onNext(new String[] {query, date});
        }
    }
}
