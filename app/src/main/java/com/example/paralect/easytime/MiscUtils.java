package com.example.paralect.easytime;

import com.example.paralect.easytime.model.Object;

import java.util.ArrayList;
import java.util.List;

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
