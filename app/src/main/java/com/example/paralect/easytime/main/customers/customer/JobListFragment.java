package com.example.paralect.easytime.main.customers.customer;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.paralect.easytime.base.AbsListFragment;
import com.example.paralect.easytime.model.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexei on 27.12.2017.
 */

public class JobListFragment extends AbsListFragment {

    public static final String ARG_JOB_LIST = "arg_job_list";

    public static JobListFragment newInstance(ArrayList<? extends Job> jobs) {
        Bundle args = new Bundle(1);
        args.putParcelableArrayList(ARG_JOB_LIST, jobs);
        JobListFragment fragment = new JobListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private List<Job> getJobs() {
        Bundle args = getArguments();
        if (args.containsKey(ARG_JOB_LIST))
            return args.getParcelableArrayList(ARG_JOB_LIST);

        // else
        return new ArrayList<>();
    }

    @Override
    public RecyclerView.Adapter buildAdapter() {
        return new JobAdapter<>(getJobs());
    }
}
