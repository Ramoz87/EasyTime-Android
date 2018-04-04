package com.example.paralect.easytime.main.projects.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.util.Pair;
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
import com.example.paralect.easytime.main.customers.customer.CustomerFragment;
import com.example.paralect.easytime.main.projects.project.invoice.ProjectInvoiceFragment;
import com.example.paralect.easytime.manager.entitysource.JobSource;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.ProjectType;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.model.User;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.utils.CollectionUtil;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.TextUtil;
import com.example.paralect.easytime.views.InfoLayout;
import com.example.paralect.easytime.views.gallery.JobFilesView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 27.12.2017.
 */

public class InformationFragment extends BaseFragment implements InformationView<Pair<Integer, List<Type>>>, AdapterView.OnItemSelectedListener {
    public static final String TAG = InformationFragment.class.getSimpleName();
    private final JobSource jobSource = new JobSource();

    @BindView(R.id.scrollView) ScrollView scrollView;
    @BindView(R.id.info_gallery_view) JobFilesView galleryFilesView;
    @BindView(R.id.instructions) InfoLayout instructions;
    @BindView(R.id.object) InfoLayout objectView;
    @BindView(R.id.otherEmployees) InfoLayout otherEmployees;
    @BindView(R.id.jobName) TextView jobName;
    @BindView(R.id.jobType) TextView jobType;
    @BindView(R.id.jobStatus) TextView jobStatus;
    @BindView(R.id.jobTerm) TextView jobTerm;
    @BindView(R.id.client) TextView client;
    @BindView(R.id.client_name) TextView clientName;
    @BindView(R.id.jobDescription) TextView jobDescription;
    @BindView(R.id.statusChooser) Spinner statusChooser;

    @BindView(R.id.address) TextView address;
    @BindView(R.id.address_layout) View addressLayout;

    @OnClick(R.id.clientZ_layout)
    void jumpToClient(View view) {
        Customer customer = job.getCustomer();
        CustomerFragment fragment = CustomerFragment.newInstance(customer);
        getMainActivity().getFragmentNavigator().pushFragment(fragment);
    }

    private InformationPresenter presenter = new InformationPresenter();
    private StatusAdapter statusAdapter = new StatusAdapter();
    private Job job;
    private int totalExpenseCountBefore = 0;

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
        presenter.subscribe();
        Logger.d("Information fragment resumed");
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unSubscribe();
    }

    private void init() {
        jobName.setText(job.getName());

        statusChooser.setAdapter(statusAdapter);
        statusChooser.setOnItemSelectedListener(this);
        presenter.setDataView(this)
                .requestData(job);

        @ProjectType.Type int type = job.getProjectType();
        if (type == ProjectType.Type.TYPE_NONE) jobType.setText(R.string.job);
        else if (type == ProjectType.Type.TYPE_PROJECT) jobType.setText(R.string.project);
        else if (type == ProjectType.Type.TYPE_OBJECT) jobType.setText(R.string.object);
        else if (type == ProjectType.Type.TYPE_ORDER) jobType.setText(R.string.order);

        Customer customer = job.getCustomer();
        clientName.setText(customer.getCompanyName());

        jobDescription.setText(job.getInformation());
        String date = CalendarUtils.stringFromDate(new Date(job.getDate()), CalendarUtils.EUROPE_DATE_FORMAT);
        if (date == null) date = "no dateTextView";
        jobTerm.setText(date);

        // AddressEntity
        if (job.getProjectType() == ProjectType.Type.TYPE_OBJECT) {
            Object object = (Object) job;
            String fullAddress = object.getFullAddress();
            if (TextUtil.isNotEmpty(fullAddress)) {
                address.setText(fullAddress);
            } else
                addressLayout.setVisibility(View.GONE);
        } else {
            addressLayout.setVisibility(View.GONE);
        }

        if (job.getProjectType() == ProjectType.Type.TYPE_ORDER) {
            Order order = (Order) job;
            instructions.addInfoItem(order.getContact(), getString(R.string.instructions_contact), null);
            instructions.addInfoItem(order.getAddress().toString(), getString(R.string.instructions_address), null);
            instructions.addInfoItem(order.getDeliveryTime(), getString(R.string.instructions_time), null);
        } else {
            instructions.setVisibility(View.GONE);
        }

        List<User> members = job.getMembers();
        if (!CollectionUtil.isEmpty(members)) {
            for (User member : members) {
                String s1 = member.getFirstName();
                String s2 = member.getLastName();
                otherEmployees.addInfoItem(String.format("%s %s", s1, s2), null);
            }
        } else {
            otherEmployees.setVisibility(View.GONE);
        }

        presenter.requestObjects(job);
        galleryFilesView.setupWithEntity(job);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_project_information, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "on prepare options menu");
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.item_new).setVisible(totalExpenseCountBefore > 0);
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
                        .pushFragment(ProjectInvoiceFragment.newInstance(job));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataReceived(Pair<Integer, List<Type>> data) {
        List<Type> types = data.second;
        totalExpenseCountBefore = data.first;
        Logger.d(TAG, String.format("received %s statuses", types.size()));
        statusAdapter.setData(types);
        for (int i = 0; i < types.size(); i++) {
            Type status = types.get(i);
            if (status.getTypeId().equals(job.getStatusId())) {
                statusChooser.setSelection(i);
            }
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onObjectsReceived(List<Object> objects) {
        if (!CollectionUtil.isEmpty(objects)) {
            for (Object object : objects) {
                objectView.addInfoItem(object.getNumberWithName(), null);
            }
            objectView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Type status = statusAdapter.getItem(i);
        Logger.d(TAG, String.format("selected status '%s'", status));
        job.setStatusId(status.getTypeId());
        jobSource.updateJob(job);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Logger.d(TAG, "nothing selected");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            presenter.requestData(job);
        }
    }

    @Override
    public Boolean needsFam() {
        return null;
    }
}
