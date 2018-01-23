package com.example.paralect.easytime.main.customers.customer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.paralect.easytime.main.AbsListFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.ProjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexei on 27.12.2017.
 */

public class JobListFragment extends AbsListFragment implements IDataView<List<Job>> {
    private static final String TAG = JobListFragment.class.getSimpleName();

    public static final String ARG_CUSTOMER = "arg_customer";
    public static final String ARG_PROJECT_TYPE = "arg_project_type";

    private JobAdapter adapter = new JobAdapter();
    private JobListPresenter presenter = new JobListPresenter();

    public static JobListFragment newInstance(@NonNull Customer customer, @ProjectType.Type int projectType) {
        Bundle args = new Bundle(2);
        args.putParcelable(ARG_CUSTOMER, customer);
        args.putInt(ARG_PROJECT_TYPE, projectType);
        JobListFragment fragment = new JobListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private @ProjectType.Type int getProjectTypeArg() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_PROJECT_TYPE))
            return args.getInt(ARG_PROJECT_TYPE);
        else return ProjectType.Type.TYPE_NONE;
    }

    private Customer getCustomerArg() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_CUSTOMER))
            return args.getParcelable(ARG_CUSTOMER);
        else return null;
    }

    @Override
    public RecyclerView.Adapter buildAdapter() {
        return adapter;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Customer customer = getCustomerArg();
        int type = getProjectTypeArg();
        Pair<Customer, Integer> parameter = new Pair<>(customer, type);
        presenter.setDataView(this)
                .requestData(parameter);
        Log.d(TAG, "requesting for project type = " + type);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {

    }

    @Override
    public boolean needsOptionsMenu() {
        return false;
    }

    @Override
    public void onDataReceived(List<Job> jobs) {
        Log.d(TAG, String.format("received %s job(s)", jobs.size()));
        adapter.setData(jobs);
    }
}
