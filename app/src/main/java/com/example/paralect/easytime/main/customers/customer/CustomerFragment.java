package com.example.paralect.easytime.main.customers.customer;

import android.content.Context;
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
import android.widget.LinearLayout;

import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.FragmentNavigator;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.CustomerContainer;
import com.example.paralect.easytime.utils.MiscUtils;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.main.MainActivity;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 27.12.2017.
 */

public class CustomerFragment extends BaseFragment implements IDataView<CustomerContainer> {

    private static final String TAG = CustomerFragment.class.getSimpleName();
    public static final String ARG_CUSTOMER = "arg_customer";

    @BindView(R.id.contacts_view_pager) ViewPager contactsViewPager;
    @BindView(R.id.contacts_page_indicator) PageIndicatorView pageIndicatorView;
    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.jobs_view_pager) ViewPager jobsViewPager;

    private final CustomerPresenter presenter = new CustomerPresenter();
    private Customer customer;
    private FragmentNavigator navigator;

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
        customer = getCustomer();
        presenter.setDataView(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigator) {
            navigator = (FragmentNavigator) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showMainTopShadow(false);
        presenter.requestData(customer);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        if (customer != null)
            actionBar.setTitle(customer.getCompanyName());
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
    public void onDataReceived(CustomerContainer container) {
        // set contacts
        final ContactsAdapter contactsAdapter = new ContactsAdapter(container.getCustomer().getContacts(), container.getCustomer().getAddress());
        contactsViewPager.setAdapter(contactsAdapter);
        pageIndicatorView.setViewPager(contactsViewPager);

        // set jobs
        final FragmentManager fm = getChildFragmentManager();
        final JobSectionPagerAdapter adapter = new JobSectionPagerAdapter(getContext(), fm, container);
        jobsViewPager.setAdapter(adapter);
        tabs.setupWithViewPager(jobsViewPager);
    }
}
