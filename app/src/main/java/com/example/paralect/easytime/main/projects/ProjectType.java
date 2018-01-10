package com.example.paralect.easytime.main.projects;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

public interface ProjectType {

    @IntDef({Type.TYPE_NONE, Type.TYPE_PROJECT, Type.TYPE_ORDER, Type.TYPE_OBJECT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int TYPE_NONE = -1;
        int TYPE_PROJECT = 0;
        int TYPE_ORDER = 1;
        int TYPE_OBJECT = 2;
    }

    @Type int getProjectType();
}


