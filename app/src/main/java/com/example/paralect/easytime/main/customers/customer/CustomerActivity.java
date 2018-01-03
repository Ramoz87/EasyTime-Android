package com.example.paralect.easytime.main.customers.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.paralect.easytime.FragmentWrapperActivity;
import com.example.paralect.easytime.MiscUtils;
import com.example.paralect.easytime.app.JobManager;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;

import java.util.List;

/**
 * Created by alexei on 27.12.2017.
 */

public class CustomerActivity extends FragmentWrapperActivity {

    public static final String ARG_CUSTOMER = "arg_customer";

    public static Intent newIntent(@NonNull Context context, Customer customer) {
        Intent intent = new Intent(context, CustomerActivity.class);
        intent.putExtra(ARG_CUSTOMER, customer);
        return intent;
    }

    private Customer customer;

    @Override
    public Fragment createFragment(Intent intent) {
        List<Job> jobs = JobManager.loadFromAssets(this);
        return CustomerFragment.newInstance(MiscUtils.findAllElements(jobs, Project.class),
                MiscUtils.findAllElements(jobs, Order.class),
                MiscUtils.findAllElements(jobs, Object.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        setTitle(customer == null ? null : customer.getCompanyName());
    }

    private void handleIntent(Intent intent) {
        if (intent.hasExtra(ARG_CUSTOMER)) {
            customer = intent.getParcelableExtra(ARG_CUSTOMER);
        }
    }
}
