package com.example.paralect.easytime.main.search;

import android.support.annotation.IdRes;
import android.view.Menu;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

public interface ISearchViewPresenter<DATA> extends IDataPresenter<DATA, String> {

    ISearchViewPresenter<DATA> setupSearch(Menu menu, @IdRes int id);
}
