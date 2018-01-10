package com.example.paralect.easytime.utils;

import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by alexei on 27.12.2017.
 */

public final class MiscUtils {

    private MiscUtils() {

    }

    public static <E> ArrayList<E> findAllElements(List list, Class<E> clazz) {
        ArrayList<E> foundElements = new ArrayList<>();
        for (java.lang.Object o : list) {
            if (o != null && clazz.isInstance(o)) {
                foundElements.add(clazz.cast(o));
            }
        }
        return foundElements;
    }
}
