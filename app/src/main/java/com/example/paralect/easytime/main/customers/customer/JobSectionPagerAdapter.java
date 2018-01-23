package com.example.paralect.easytime.main.customers.customer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.ProjectType;

/**
 * Created by alexei on 27.12.2017.
 */

public class JobSectionPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private Customer mCustomer;

    public JobSectionPagerAdapter(Context context, FragmentManager fm, Customer customer) {
        super(fm);
        mContext = context;
        mCustomer = customer;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return JobListFragment.newInstance(mCustomer, ProjectType.Type.TYPE_PROJECT);
            case 1: return JobListFragment.newInstance(mCustomer, ProjectType.Type.TYPE_ORDER);
            case 2: return JobListFragment.newInstance(mCustomer, ProjectType.Type.TYPE_OBJECT);
            default: return JobListFragment.newInstance(mCustomer, ProjectType.Type.TYPE_NONE);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:return mContext.getString(R.string.projects);
            case 1:return mContext.getString(R.string.orders);
            case 2:return mContext.getString(R.string.objects);
            default: return "none";
        }
    }
}
