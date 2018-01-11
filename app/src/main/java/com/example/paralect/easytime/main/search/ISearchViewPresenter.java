package com.example.paralect.easytime.main.search;

import android.support.annotation.IdRes;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.TextView;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

public interface ISearchViewPresenter<DATA> extends IDataPresenter<DATA, String> {

    ISearchViewPresenter<DATA> setupQuerySearch(SearchView searchView);

    ISearchViewPresenter<DATA> setupDateSearch(TextView view);

    ISearchViewPresenter<DATA> setDataView(IDataView<DATA> view);

    ISearchViewPresenter<DATA> requestData(String[] parameters);
}
