package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.manager.EasyTimeManager;
import com.paralect.easytimedataormlite.DatabaseHelperORMLite;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

class EntitySource {

    public static final String TAG = ExpenseSource.class.getSimpleName();

    protected final DatabaseHelperORMLite dataSource = EasyTimeManager.getInstance().getDataSource();
}
