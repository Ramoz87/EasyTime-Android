package com.example.paralect.easytime.main.projects.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Job;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 27.12.2017.
 */

public class ProjectFragment extends Fragment {

    public static final String ARG_JOB = "arg_job";

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private Job job;

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    public static ProjectFragment newInstance(@NonNull Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_JOB, job);
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_project, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        job = getJobArg();
        init();
    }

    private void init() {
        Job job = getJobArg();
        FragmentPagerAdapter adapter = new ProjectSectionAdapter(getContext(), getChildFragmentManager(), job);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    private Job getJobArg() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_JOB))
            return args.getParcelable(ARG_JOB);
        else return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }
}
