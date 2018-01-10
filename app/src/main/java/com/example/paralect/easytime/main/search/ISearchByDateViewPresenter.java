package com.example.paralect.easytime.main.search;

import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by alexei on 10.01.2018.
 */

public interface ISearchByDateViewPresenter<DATA> {
    ISearchByDateViewPresenter<DATA> setupSearch(TextView view);

    ISearchByDateViewPresenter<DATA> setSearchDataView(ISearchDataView<DATA> view);

    ISearchByDateViewPresenter<DATA> requestData(Calendar calendar);
}
