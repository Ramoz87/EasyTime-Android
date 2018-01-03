package com.example.paralect.easytime.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

public final class JobManager {

    public static List<Job> loadFromAsset(@NonNull Context context) {
        List<Job> jobs = new ArrayList<>();

        for (int i = 0; i < 28; i++) {
            jobs.add(new Project());
        }

        for (int i = 0; i < 25; i++) {
            jobs.add(new Object());
        }

        for (int i = 0; i < 27; i++) {
            jobs.add(new Order());
        }

        for (int j = 0; j < jobs.size(); j++) {
            Job job = jobs.get(j);
            job.setJobId(j);
            job.setName(job.getClass().getSimpleName() + ": Repair museum");
        }
        return jobs;
    }
}
