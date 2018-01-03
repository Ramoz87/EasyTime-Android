package com.example.paralect.easytime.main.customers.customer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;

import java.util.ArrayList;

/**
 * Created by alexei on 27.12.2017.
 */

public class JobSectionPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private ArrayList<Project> projects;
    private ArrayList<Order> orders;
    private ArrayList<Object> objects;

    public JobSectionPagerAdapter(Context context, FragmentManager fm, ArrayList<Project> projects, ArrayList<Order> orders, ArrayList<Object> objects) {
        super(fm);
        this.context = context;
        this.projects = projects;
        this.orders = orders;
        this.objects = objects;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return JobListFragment.newInstance(projects);
            case 1: return JobListFragment.newInstance(orders);
            case 2: return JobListFragment.newInstance(objects);
            default: return JobListFragment.newInstance(new ArrayList<Job>());
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:return context.getString(R.string.projects);
            case 1:return context.getString(R.string.orders);
            case 2:return context.getString(R.string.objects);
            default: return "none";
        }
    }
}
