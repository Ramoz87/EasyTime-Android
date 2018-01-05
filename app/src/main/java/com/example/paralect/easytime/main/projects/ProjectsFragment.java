package com.example.paralect.easytime.main.projects;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.paralect.easytime.main.FragmentNavigator;
import com.example.paralect.easytime.base.AbsStickyFragment;
import com.example.paralect.easytime.ActionBarUtils;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.app.EasyTimeManager;
import com.example.paralect.easytime.main.projects.project.ProjectFragment;
import com.example.paralect.easytime.model.Job;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 26.12.2017.
 */

public class ProjectsFragment extends AbsStickyFragment {
    private static final String TAG = ProjectsFragment.class.getSimpleName();


    private FragmentNavigator navigator;
    private ProjectStickyAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (FragmentNavigator) context;
    }

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    private List<Job> getJobs() {
        return EasyTimeManager.getJobs(getContext());
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        Log.d(TAG, "building sticky adapter");
        List<Job> jobs = getJobs();
        return (adapter = new ProjectStickyAdapter(jobs));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBarUtils.setTitle(getActivity(), R.string.nav_projects);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Job job = adapter.getItem(i);
        ProjectFragment fragment = ProjectFragment.newInstance(job);
        navigator.pushFragment(fragment);
    }
}
