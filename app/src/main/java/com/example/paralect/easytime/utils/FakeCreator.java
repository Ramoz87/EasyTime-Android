package com.example.paralect.easytime.utils;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexei on 03.01.2018.
 */

// this class is used to parse some data from csv files and create objects

public abstract class FakeCreator {
    private static final String TAG = FakeCreator.class.getSimpleName();

    private AssetManager am;

    public FakeCreator(@NonNull AssetManager am) {
        this.am = am;
    }

    public <E> List<E> parse(final String csvPath, Class<E> clazz) throws IOException {
        List<E> items = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(am.open(csvPath)));
        CSVReader reader = new CSVReader(bufferedReader);
        String [] lines;
        int index = 0;
        while ((lines = reader.readNext()) != null) {
            if (index++ == 0) continue;
            E item = (E) create(clazz, lines);
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    public abstract <E> Object create(Class<E> clazz, String[] fields);
}
