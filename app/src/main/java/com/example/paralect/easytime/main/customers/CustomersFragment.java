package com.example.paralect.easytime.main.customers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.paralect.easytime.base.AbsStickyFragment;
import com.example.paralect.easytime.ActionBarUtils;
import com.example.paralect.easytime.MiscUtils;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.app.JobManager;
import com.example.paralect.easytime.main.customers.customer.CustomerFragment;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.CustomerComparator;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 26.12.2017.
 */

public class CustomersFragment extends AbsStickyFragment {

    private CustomerComparator comparator;

    public static CustomersFragment newInstance() {
        return new CustomersFragment();
    }

    private void initComparator() {
        if (comparator == null)
            comparator = new CustomerComparator();
    }

    private List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer c1 = new Customer();
        c1.setCompanyName("Paralect");
        customers.add(c1);

        Customer c2 = new Customer();
        c2.setCompanyName("YAAA");
        customers.add(c2);

        Customer c3 = new Customer();
        c3.setCompanyName("AAB");
        customers.add(c3);

        Customer c4 = new Customer();
        c4.setCompanyName("FFFF");
        customers.add(c4);

        Customer c5 = new Customer();
        c5.setCompanyName("XXA");
        customers.add(c5);

        Customer c6 = new Customer();
        c6.setCompanyName("E1111");
        customers.add(c6);

        Customer c7 = new Customer();
        c7.setCompanyName("0uuu1231");
        customers.add(c7);

        Customer c8 = new Customer();
        c8.setCompanyName("REAL");
        customers.add(c8);

        Customer c9 = new Customer();
        c9.setCompanyName("CBA");
        customers.add(c9);

        return customers;
    }

    private SortedMap<Character, List<Customer>> getSortedCustomers(List<Customer> customers) {
        // split list of customers alphabetically
        initComparator();
        Collections.sort(customers, comparator);
        SortedMap<Character, List<Customer>> map = new TreeMap<>();
        for (Customer c : customers) {
            String companyName = c.getCompanyName();
            char firstChar = companyName.charAt(0);
            if (map.containsKey(firstChar)) {
                map.get(firstChar).add(c);
            } else {
                List<Customer> newSection = new ArrayList<>();
                newSection.add(c);
                map.put(firstChar, newSection);
            }
        }
        return map;
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        List<Customer> customers = getCustomers();
        SortedMap<Character, List<Customer>> map = getSortedCustomers(customers);
        return new CustomerStickyAdapter(map);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // setHasOptionsMenu(true);
        return super.onCreateView(inflater, parent, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBarUtils.setTitle(getActivity(), R.string.nav_clients);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Customer customer = new Customer();
        customer.setCompanyName("Mobexs");
        // Intent intent = CustomerActivity.newIntent(getContext(), customer);
        // startActivity(intent);
        List<Job> jobs = JobManager.loadFromAsset(getContext());
        CustomerFragment fragment = CustomerFragment.newInstance(MiscUtils.findAllElements(jobs, Project.class),
                MiscUtils.findAllElements(jobs, Order.class),
                MiscUtils.findAllElements(jobs, Object.class));
        // ((MainActivity) getActivity()).addFragment(fragment, CustomerFragment.class.getSimpleName());
//        getChildFragmentManager().beginTransaction()
//                .addToBackStack(null)
//                .add(fragment, CustomerFragment.class.getSimpleName())
//                .commit();
        pushFragment(fragment);
    }
}
