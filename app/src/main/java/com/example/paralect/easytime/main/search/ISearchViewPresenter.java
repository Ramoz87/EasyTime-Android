package com.example.paralect.easytime.main.search;

import android.support.v7.widget.SearchView;
import android.widget.TextView;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

public interface ISearchViewPresenter<DATA> {

    ISearchViewPresenter<DATA> setupQuerySearch(SearchView searchView);

    ISearchViewPresenter<DATA> setupDateSearch(TextView view);

    ISearchViewPresenter<DATA> setSearchDataView(ISearchDataView<DATA> view);

    ISearchViewPresenter<DATA> requestData(String query, String date);
}
