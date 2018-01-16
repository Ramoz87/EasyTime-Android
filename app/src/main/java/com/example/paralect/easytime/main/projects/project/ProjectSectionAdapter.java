package com.example.paralect.easytime.main.projects.project;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.views.BaseFragmentPagerAdapter;

/**
 * Created by alexei on 27.12.2017.
 */

public class ProjectSectionAdapter extends BaseFragmentPagerAdapter {

    private Context context;

    public ProjectSectionAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment onCreateFragment(int position) {
        switch (position) {
            case 0: return ActivityFragment.newInstance();
            case 1: return InformationFragment.newInstance();
            default: return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:return context.getString(R.string.activity);
            case 1:return context.getString(R.string.information);
            default: return "none";
        }
    }
}
