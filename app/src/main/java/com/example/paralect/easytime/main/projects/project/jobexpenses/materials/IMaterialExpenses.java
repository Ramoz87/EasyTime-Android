package com.example.paralect.easytime.main.projects.project.jobexpenses.materials;

import com.example.paralect.easytime.main.IDataView;

/**
 * Created by Oleg Tarashkevich on 23/01/2018.
 */

interface IMaterialExpenses<DATA> extends IDataView<DATA> {

    void onFinishing();
}
