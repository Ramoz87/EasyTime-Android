package com.example.paralect.easytime.main.projects.project;

import com.example.paralect.easytime.main.IDataView;

import com.example.paralect.easytime.model.Object;

import java.util.List;

/**
 * Created by Oleg Tarashkevich on 07/02/2018.
 */

interface InformationView<DATA> extends IDataView<DATA> {

    void onObjectsReceived(List<Object> objects);
}
