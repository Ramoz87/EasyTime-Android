package com.example.paralect.easytime.model;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by alexei on 17.01.2018.
 */

public class JobComparator implements Comparator<Job> {

    @Override
    public int compare(Job job, Job t1) {
        if (job == null && t1 == null) return 0;

        if (job == null) return -1;

        if (t1 == null) return 1;

        String name1 = job.getName();
        String name2 = job.getName();

        if (name1 == null && name2 == null) return 0;

        if (name1== null) return -1;

        if (name2 == null) return 1;

        return name1.compareTo(name2);
    }
}
