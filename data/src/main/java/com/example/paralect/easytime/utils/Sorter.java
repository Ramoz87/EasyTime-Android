package com.example.paralect.easytime.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.paralect.easytime.model.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by alexei on 04.01.2018.
 */

// helper class to sort list of items of some type and split them into sections
// each section has an unique character(header)
public abstract class Sorter<E> {

    public Sorter() {

    }

    public abstract char getCharacterForItem(E item);

    public SortedMap<Character, List<E>> getSortedItems(@NonNull List<E> items, @Nullable Comparator<E> comparator) {
        if (comparator != null) { // a chance to sort list of items
            Collections.sort(items, comparator);
        }
        SortedMap<Character, List<E>> map = new TreeMap<>();
        for (E item : items) {
            char c = getCharacterForItem(item);
            if (map.containsKey(c)) {
                map.get(c).add(item);
            } else {
                List<E> newSection = new ArrayList<>();
                newSection.add(item);
                map.put(c, newSection);
            }
        }
        return map;
    }
}
