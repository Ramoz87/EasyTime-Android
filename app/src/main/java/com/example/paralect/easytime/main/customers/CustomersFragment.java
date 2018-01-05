package com.example.paralect.easytime.main.customers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.paralect.easytime.Sorter;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.ActionBarUtils;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.app.EasyTimeManager;
import com.example.paralect.easytime.main.FragmentNavigator;
import com.example.paralect.easytime.main.customers.customer.CustomerFragment;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.CustomerComparator;

import java.util.List;
import java.util.SortedMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 26.12.2017.
 */

public class CustomersFragment extends AbsStickyFragment {

    private CustomerStickyAdapter adapter;
    private CustomerComparator comparator;
    private final Sorter<Customer> sorter = new Sorter<Customer>() {
        @Override
        public char getCharacterForItem(Customer item) {
            String companyName = item.getCompanyName();
            return companyName.charAt(0);
        }
    };
    private FragmentNavigator navigator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (FragmentNavigator) context;
    }

    public static CustomersFragment newInstance() {
        return new CustomersFragment();
    }

    private void initComparator() {
        if (comparator == null)
            comparator = new CustomerComparator();
    }

    private List<Customer> getCustomers() {
        return EasyTimeManager.getCustomers(getContext().getApplicationContext());
    }

    private SortedMap<Character, List<Customer>> getSortedCustomers(List<Customer> customers) {
        // split list of customers alphabetically
        initComparator();
        return sorter.getSortedItems(customers, comparator);
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        List<Customer> customers = getCustomers();
        SortedMap<Character, List<Customer>> map = getSortedCustomers(customers);
        return (adapter = new CustomerStickyAdapter(map));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // setHasOptionsMenu(true);
        return super.onCreateView(inflater, parent, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        actionBar.setTitle(R.string.nav_clients);
        // ActionBarUtils.setTitle(getActivity(), R.string.nav_clients);
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Customer customer = adapter.getItem(i);
        CustomerFragment fragment = CustomerFragment.newInstance(customer);
        navigator.pushFragment(fragment);
    }

}
