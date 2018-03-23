package com.example.paralect.easytime.model;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by alexei on 17.01.2018.
 */

public class ObjectComparator implements Comparator<Object> {

    @Override
    public int compare(Object object, Object t1) {
        if (object == null && t1 == null) return 0;

        if (object == null) return -1;

        if (t1 == null) return 1;

        String name1 = object.getName();
        String name2 = object.getName();

        if (name1 == null && name2 == null) return 0;

        if (name1== null) return -1;

        if (name2 == null) return 1;

        return name1.compareTo(name2);
    }
}
