package com.example.paralect.easytime.main.search;

import android.support.annotation.IdRes;
import android.view.Menu;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

public interface ISearchViewPresenter<DATA> {

    ISearchViewPresenter<DATA> setupSearch(Menu menu, @IdRes int id);

    ISearchViewPresenter<DATA> setSearchDataView(ISearchDataView<DATA> view);

    // perform a query
    ISearchViewPresenter<DATA> requestData(String query);
}
