package com.example.paralect.easytime.main.search;

import android.support.annotation.IdRes;
import android.view.Menu;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

public interface ISearchViewPresenter<DATA> {

    void setupSearch(Menu menu, @IdRes int id, ISearchView<DATA> view);

    // perform a query
    void requestData(String query);
}
