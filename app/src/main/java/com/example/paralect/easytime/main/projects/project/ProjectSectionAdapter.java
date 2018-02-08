package com.example.paralect.easytime.main.projects.project;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.views.BaseFragmentPagerAdapter;
import com.github.clans.fab.FloatingActionMenu;

/**
 * Created by alexei on 27.12.2017.
 */

public class ProjectSectionAdapter extends BaseFragmentPagerAdapter {

    private Context context;
    private Job job;

    public ProjectSectionAdapter(Context context, FragmentManager fm, Job job) {
        super(fm);
        this.context = context;
        this.job = job;
    }

    @Override
    public Fragment onCreateFragment(int position) {
        switch (position) {
            case 0: return ActivityFragment.newInstance(job);
            case 1: return InformationFragment.newInstance(job);
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
