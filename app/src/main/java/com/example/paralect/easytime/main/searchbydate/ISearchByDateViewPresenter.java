package com.example.paralect.easytime.main.searchbydate;

import android.widget.TextView;

/**
 * Created by alexei on 10.01.2018.
 */

public interface ISearchByDateViewPresenter<DATA> {
    ISearchByDateViewPresenter<DATA> setupSearch(TextView view);
    ISearchByDateViewPresenter<DATA> setSearchDataView(ISearchDataByDateView<DATA> view);
    ISearchByDateViewPresenter<DATA> requestData(int year, int month, int day);
}
