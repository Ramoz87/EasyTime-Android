package com.example.paralect.easytime.main.customers.customer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.JobsContainer;
import com.example.paralect.easytime.utils.ViewUtils;
import com.rd.PageIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 27.12.2017.
 */

public class CustomerFragment extends BaseFragment implements IDataView<Customer>, ICustomerDataView<JobsContainer> {

    public static final String ARG_CUSTOMER = "arg_customer";

    @BindView(R.id.contacts_view_pager) ViewPager contactsViewPager;
    @BindView(R.id.contacts_page_indicator) PageIndicatorView pageIndicatorView;
    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.jobs_view_pager) ViewPager jobsViewPager;

    private final CustomerPresenter presenter = new CustomerPresenter();
    private Customer mCustomer;

    public static CustomerFragment newInstance(@NonNull Customer customer) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_CUSTOMER, customer);
        CustomerFragment fragment = new CustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomer = getCustomer();
        presenter.setDataView(this);
        presenter.setJobsDataView(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showMainTopShadow(false);
        presenter.requestData(mCustomer);
        presenter.requestForJobs(mCustomer);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        if (mCustomer != null)
            actionBar.setTitle(mCustomer.getCompanyName());
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    private Customer getCustomer() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_CUSTOMER))
            return args.getParcelable(ARG_CUSTOMER);
        else return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showMainTopShadow(true);
    }

    @Override
    public void onDataReceived(Customer customer) {
        // set contacts
        if (customer != null) {
            final ContactsAdapter contactsAdapter = new ContactsAdapter(customer.getContacts(), customer.getAddress());
            contactsViewPager.setAdapter(contactsAdapter);
            pageIndicatorView.setViewPager(contactsViewPager);
            if (customer.getContacts() != null) {
                ViewUtils.setVisibility(pageIndicatorView, customer.getContacts().size() > 1);
            }
        }
    }

    @Override
    public void onJobsReceived(JobsContainer jobs) {
        // set jobs
        if (jobs != null) {
            final FragmentManager fm = getChildFragmentManager();
            final JobSectionPagerAdapter adapter = new JobSectionPagerAdapter(getContext(), fm, getCustomer());
            jobsViewPager.setAdapter(adapter);
            tabs.setupWithViewPager(jobsViewPager);
        }
    }

}
