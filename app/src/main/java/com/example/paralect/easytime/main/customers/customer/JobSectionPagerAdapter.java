package com.example.paralect.easytime.main.customers.customer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.ProjectType;

import java.util.List;

/**
 * Created by alexei on 27.12.2017.
 */

public class JobSectionPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private Customer mCustomer;
    private List<Integer> types;

    public JobSectionPagerAdapter(Context context, FragmentManager fm, Customer customer, List<Integer> types) {
        super(fm);
        mContext = context;
        mCustomer = customer;
        this.types = types;
    }

    @Override
    public Fragment getItem(int position) {
        return JobListFragment.newInstance(mCustomer, types.get(position));
    }

    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        @ProjectType.Type int type = types.get(position);
        switch (type) {
            case ProjectType.Type.TYPE_PROJECT:return mContext.getString(R.string.projects);
            case ProjectType.Type.TYPE_ORDER:return mContext.getString(R.string.orders);
            case ProjectType.Type.TYPE_OBJECT:return mContext.getString(R.string.objects);
            default: return "none";
        }
    }
}
