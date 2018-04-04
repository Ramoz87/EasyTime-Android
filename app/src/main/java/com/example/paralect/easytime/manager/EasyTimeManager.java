package com.example.paralect.easytime.manager;

import com.example.paralect.easytime.EasyTimeApplication;
import com.paralect.easytimedataormlite.DatabaseHelperORMLite;

/**
 * Created by alexei on 26.12.2017.
 */

public final class EasyTimeManager {
    private static final String TAG = EasyTimeManager.class.getSimpleName();

    private volatile static EasyTimeManager instance;
    private DatabaseHelperORMLite dataSource;

    /**
     * Returns singleton class instance
     */
    public static EasyTimeManager getInstance() {
        EasyTimeManager localInstance = instance;

        if (localInstance == null) {
            synchronized (EasyTimeManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EasyTimeManager();
                }
            }
        }
        return localInstance;
    }

    private EasyTimeManager() {
        if (dataSource == null)
            dataSource = new DatabaseHelperORMLite(EasyTimeApplication.getContext());
    }

    public DatabaseHelperORMLite getDataSource() {
        return dataSource;
    }
}
