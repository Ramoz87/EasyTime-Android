package com.example.paralect.easytime.main.projects;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.paralect.easytime.base.AbsStickyFragment;
import com.example.paralect.easytime.ActionBarUtils;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.app.JobManager;
import com.example.paralect.easytime.main.MainActivity;
import com.example.paralect.easytime.main.customers.customer.CustomerFragment;
import com.example.paralect.easytime.main.projects.project.ProjectFragment;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Project;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 26.12.2017.
 */

public class ProjectListFragment extends AbsStickyFragment {
    private static final String TAG = ProjectListFragment.class.getSimpleName();

    public static final String ARG_JOBS = "arg_jobs";

    public static ProjectListFragment newInstance(List<Job> jobs) {
        Bundle bundle = new Bundle(1);
        // bundle.putParcelableArrayList(ARG_JOBS, jobs);
        return new ProjectListFragment();
    }

    private List<Job> getJobs() {
        return JobManager.loadFromAsset(getContext());
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        Log.d(TAG, "building sticky adapter");
        List<Job> jobs = getJobs();
        return new ProjectStickyAdapter(jobs);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBarUtils.setTitle(getActivity(), R.string.nav_projects);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ProjectFragment fragment = ProjectFragment.newInstance();
        // ((MainActivity) getActivity()).addFragment(fragment, Project.class.getSimpleName());
//        getChildFragmentManager().beginTransaction()
//                .addToBackStack(null)
//                .add(fragment, ProjectListFragment.class.getSimpleName())
//                .commit();
        pushFragment(fragment);
    }
}
