package com.example.paralect.easytime.main.searchbydate;

import android.app.DatePickerDialog;
import android.text.SpannableString;
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

    private static final int DELAY = 200;
    private PublishProcessor<Calendar> mPublisher;
    protected ISearchDataByDateView<DATA> mView;
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
            mPublisher = PublishProcessor.create();
            final Flowable<Calendar> flowable = mPublisher.onBackpressureBuffer();
            flowable.debounce(DELAY, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Calendar>() {
                        @Override
                        public void accept(Calendar query) throws Exception {
                            requestData(query.get(Calendar.YEAR), query.get(Calendar.MONTH), query.get(Calendar.DAY_OF_MONTH));
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
    public ISearchByDateViewPresenter<DATA> setSearchDataView(ISearchDataByDateView<DATA> view) {
        this.mView = view;
        return this;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        year = i;
        month = i1;
        day = i2;
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SpannableString spannableDateString = CalendarUtils.getSpannableDateString(mTextView.getContext(), calendar);
        mTextView.setText(spannableDateString);

        if (mPublisher != null) {
            mPublisher.onNext(calendar);
        }
    }
}
