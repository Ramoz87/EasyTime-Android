package com.example.paralect.easytime.main.projects.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.main.customers.customer.CustomerFragment;
import com.example.paralect.easytime.main.projects.project.details.ProjectDetailsFragment;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.ProjectType;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.views.InfoLayout;
import com.example.paralect.easytime.views.gallery.JobFilesView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 27.12.2017.
 */

public class InformationFragment extends BaseFragment implements IDataView<List<Type>>, AdapterView.OnItemSelectedListener {
    public static final String TAG = InformationFragment.class.getSimpleName();

    @BindView(R.id.scrollView) ScrollView scrollView;
    @BindView(R.id.info_gallery_view) JobFilesView galleryFilesView;
    @BindView(R.id.instructions) InfoLayout instructions;
    @BindView(R.id.jobName) TextView jobName;
    @BindView(R.id.jobType) TextView jobType;
    @BindView(R.id.jobStatus) TextView jobStatus;
    @BindView(R.id.jobTerm) TextView jobTerm;
    @BindView(R.id.client) TextView client;
    @BindView(R.id.jobDescription) TextView jobDescription;
    @BindView(R.id.statusChooser) Spinner statusChooser;

    @OnClick(R.id.client)
    void jumpToClient(View view) {
        Customer customer = job.getCustomer();
        CustomerFragment fragment = CustomerFragment.newInstance(customer);
        getMainActivity().getFragmentNavigator().pushFragment(fragment);
    }

    private StatusPresenter statusPresenter = new StatusPresenter();
    private StatusAdapter statusAdapter = new StatusAdapter();
    private Job job;
    private String date;

    public static InformationFragment newInstance(@NonNull Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(Job.TAG, job);
        InformationFragment fragment = new InformationFragment();
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
        return inflater.inflate(R.layout.fragment_project_information, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        statusPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        statusPresenter.unSubscribe();
    }

    private void init() {
        jobName.setText(job.getName());
        // jobDescription.setText();

        statusChooser.setAdapter(statusAdapter);
        statusChooser.setOnItemSelectedListener(this);
        statusPresenter.setDataView(this)
                .requestData(null);

        @ProjectType.Type int type = job.getProjectType();
        if (type == ProjectType.Type.TYPE_NONE) jobType.setText(R.string.job);
        else if (type == ProjectType.Type.TYPE_PROJECT) jobType.setText(R.string.project);
        else if (type == ProjectType.Type.TYPE_OBJECT) jobType.setText(R.string.object);
        else if (type == ProjectType.Type.TYPE_ORDER) jobType.setText(R.string.order);

        Customer customer = job.getCustomer();
        // client.setText(customer.getCompanyName());

        jobDescription.setText(job.getInformation());
        String date = job.getDate();
        if (date == null) date = "no dateTextView";
        jobTerm.setText(date);

        if (job.getProjectType() == ProjectType.Type.TYPE_ORDER) {
            Order order = (Order) job;
            instructions.addInfoItem(R.drawable.ic_watch, order.getDeliveryTime(), null);
            instructions.addInfoItem(R.drawable.ic_phone, order.getContact(), null);
            instructions.addInfoItem(R.drawable.ic_checkpoint, order.getAddress().toString(), null);
        } else {
            instructions.setVisibility(View.GONE);
        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_new: {
                getMainActivity().getFragmentNavigator()
                        .pushFragment(ProjectDetailsFragment.newInstance(job, statusPresenter.getDate()));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataReceived(List<Type> types) {
        Log.d(TAG, String.format("received %s statuses", types.size()));
        statusAdapter.setData(types);
        for (int i = 0; i < types.size(); i++) {
            Type status = types.get(i);
            if (status.getTypeId().equals(job.getStatusId())) {
                statusChooser.setSelection(i);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Type status = statusAdapter.getItem(i);
        Log.d(TAG, String.format("selected status '%s'", status));
        job.setStatusId(status.getTypeId());
        EasyTimeManager.getInstance().updateJob(job);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(TAG, "nothing selected");
    }
}
