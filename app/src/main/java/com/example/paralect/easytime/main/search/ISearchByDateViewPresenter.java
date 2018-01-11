package com.example.paralect.easytime.main.search;

import android.widget.TextView;

import com.example.paralect.easytime.main.IDataPresenter;

import java.util.Calendar;

/**
 * Created by alexei on 10.01.2018.
 */

public interface ISearchByDateViewPresenter<DATA> extends IDataPresenter<DATA, Calendar> {

    ISearchByDateViewPresenter<DATA> setupSearch(TextView view);
}
