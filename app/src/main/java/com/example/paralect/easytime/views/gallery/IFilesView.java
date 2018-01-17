package com.example.paralect.easytime.views.gallery;

import android.content.Context;

import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.model.File;

import java.util.List;

/**
 * Created by Oleg Tarashkevich on 16/01/2018.
 */

interface IFilesView<DATA, E> extends IDataView<DATA>{

    Context getViewContext();

    void setupWithEntity(E entity);
}
