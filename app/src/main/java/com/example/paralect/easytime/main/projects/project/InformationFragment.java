package com.example.paralect.easytime.main.projects.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.ProjectType;
import com.example.paralect.easytime.views.InfoLayout;
import com.example.paralect.easytime.views.gallery.JobFilesView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 27.12.2017.
 */

public class InformationFragment extends BaseFragment {
    public static final String TAG = InformationFragment.class.getSimpleName();

    public static final String ARG_JOB = "arg_job";

    @BindView(R.id.scrollView) ScrollView scrollView;
    @BindView(R.id.info_gallery_view) JobFilesView galleryFilesView;
    @BindView(R.id.instructions) InfoLayout instructions;
    @BindView(R.id.jobName) TextView jobName;
    @BindView(R.id.jobType) TextView jobType;
    @BindView(R.id.jobStatus) TextView jobStatus;
    @BindView(R.id.companyName) TextView companyName;
    @BindView(R.id.jobDescription) TextView jobDescription;

    private Job job;

    public static InformationFragment newInstance(@NonNull Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_JOB, job);
        InformationFragment fragment = new InformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Job getJobArg() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_JOB))
            return args.getParcelable(ARG_JOB);
        else return null;
    }

    private void initJob() {
        if (job == null)
            job = getJobArg();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_information, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        initJob();
        jobName.setText(job.getName());
        // jobDescription.setText();

        @ProjectType.Type int type = job.getProjectType();
        if (type == ProjectType.Type.TYPE_NONE) jobType.setText(R.string.job);
        else if (type == ProjectType.Type.TYPE_PROJECT) jobType.setText(R.string.project);
        else if (type == ProjectType.Type.TYPE_OBJECT) jobType.setText(R.string.object);
        else if (type == ProjectType.Type.TYPE_ORDER) jobType.setText(R.string.order);

        Customer customer = job.getCustomer();
        companyName.setText(customer.getCompanyName());

        instructions.addInfoItem(R.drawable.ic_watch, R.string.placeholder_project_info_delivery_time, null);
        instructions.addInfoItem(R.drawable.ic_phone, R.string.placeholder_project_info_contact, null);
        instructions.addInfoItem(R.drawable.ic_checkpoint, R.string.placeholder_project_info_address, null);

        Job job = getJobArg();
        galleryFilesView.setupWithEntity(job);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_project_information, menu);
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {

    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

}
