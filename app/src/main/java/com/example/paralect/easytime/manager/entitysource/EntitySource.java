package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.manager.DataManager;
import com.paralect.easytimedataormlite.DatabaseHelper;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

class EntitySource {

    public static final String TAG = ExpenseSource.class.getSimpleName();

    protected final DatabaseHelper dataSource = DataManager.getInstance().getDataSource();
}
