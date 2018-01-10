package com.example.paralect.easytime.main.search;

import android.app.DatePickerDialog;
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
 * Created by alexei on 10.01.2018.
 */

public abstract class SearchByDateViewPresenter<DATA> implements ISearchByDateViewPresenter<DATA>, DatePickerDialog.OnDateSetListener {

    private PublishProcessor<Calendar> mPublisher;
    protected ISearchDataView<DATA> mView;
    private TextView mTextView;

    private int year;
    private int month;
    private int day;

    public SearchByDateViewPresenter(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private void setupPublisher() {
        if (mPublisher == null) {
            mPublisher = SearchPresenterUtils.createPublisherProcessor(new Consumer<Calendar>() {
                @Override
                public void accept(Calendar query) throws Exception {
                    requestData(query);
                }
            });
        }
    }

    @Override
    public ISearchByDateViewPresenter<DATA> setupSearch(TextView view) {
        setupPublisher();
        this.mTextView = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), SearchByDateViewPresenter.this, year, month, day);
                datePickerDialog.show();
            }
        });
        return this;
    }

    @Override
    public ISearchByDateViewPresenter<DATA> setSearchDataView(ISearchDataView<DATA> view) {
        this.mView = view;
        return this;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        year = i;
        month = i1;
        day = i2;
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SpannableString spannableDateString = CalendarUtils.getSpannableDateString(mTextView.getContext(), calendar);
        mTextView.setText(spannableDateString);

        Log.d("NewDate", "here " + CalendarUtils.stringFromDate(calendar.getTime(), CalendarUtils.DEFAULT_DATE_FORMAT));

        if (mPublisher != null) {
            mPublisher.onNext(calendar);
        }
    }
}
