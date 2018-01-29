package com.example.paralect.easytime.main.projects.project;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.IntentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 27.12.2017.
 */

public class ProjectFragment extends BaseFragment {

    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.view_pager) ViewPager viewPager;

    private FragmentPagerAdapter adapter;
    private Job job;
    private ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            invalidateFragmentMenus(position);
        }
    };

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    public static ProjectFragment newInstance(@NonNull Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(Job.TAG, job);
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        job = Job.fromBundle(getArguments());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    private void init() {
        adapter = new ProjectSectionAdapter(getContext(), getChildFragmentManager(), job);
        viewPager.setAdapter(adapter);
        viewPager.removeOnPageChangeListener(pageChangeListener);
        viewPager.addOnPageChangeListener(pageChangeListener);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        String number = getResources().getString(R.string.job_number, job.getNumber());
        actionBar.setTitle(number);
    }

    @Override
    public boolean needsOptionsMenu() {
        return false;
    }

    private void invalidateFragmentMenus(int position) {
        for (int i = 0; i < adapter.getCount(); i++) {
            adapter.getItem(i).setHasOptionsMenu(i == position);
        }
        Activity activity = getActivity();
        if (!IntentUtils.isFinishing(activity))
            activity.invalidateOptionsMenu();
    }

}
