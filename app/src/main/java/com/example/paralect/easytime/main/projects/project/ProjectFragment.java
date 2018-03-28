package com.example.paralect.easytime.main.projects.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.anim.AnimUtils;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 27.12.2017.
 */

public class ProjectFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private static final String TAG = ProjectFragment.class.getSimpleName();

    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.view_pager) ViewPager viewPager;

    private ProjectSectionAdapter adapter;
    private Job job;
    private float originalFamX;
    private Animation fullInc;
    private Animation fullDec;
    private FloatingActionMenu fam;
    private ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            Logger.d(TAG, String.format("selected page i = %s", position));
            invalidateFragmentMenus(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            float percentageOfVisibility = 1 - positionOffset;
            float x = originalFamX * percentageOfVisibility;
            Logger.d(TAG, String.format("position = %s, visibility = %s, x = %s", position, percentageOfVisibility, x));
            if (fam == null) {
                Log.d(TAG, "fam == null, ignore scrolling");
                return;
            }
            if (position == 1) {
                fam.setVisibility(View.GONE);
                return;
            }

            if (percentageOfVisibility < 0.8f) {
                Log.d(TAG, "hiding");
                fam.setVisibility(View.GONE);
            } else if (percentageOfVisibility >= 0.8f) {
                Log.d(TAG, "showing");
                fam.setVisibility(View.VISIBLE);
            }
        }
    };

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    public static ProjectFragment newInstance(@NonNull Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(Job.TAG, job);
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        job = Job.fromBundle(getArguments());
    }

    private void initFam() {
        if (fam == null) {
            fam = getFam();
        }
    }

    private void initAnimations() {
        if (fullInc == null) {
            int duration = 100;
            fullInc = AnimationUtils.loadAnimation(getContext(), R.anim.full_inc);
            fullInc.setAnimationListener(AnimUtils.newAppearingAnimListener(fam));
            fullInc.setDuration(duration);
        }

        if (fullDec == null) {
            int duration = 100;
            fullDec = AnimationUtils.loadAnimation(getContext(), R.anim.full_dec);
            fullDec.setAnimationListener(AnimUtils.newDisappearingAnimListener(fam));
            fullDec.setDuration(duration);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initFam();
        initAnimations();
        init();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    private void init() {
        originalFamX = getFam().getX();
        adapter = new ProjectSectionAdapter(getContext(), getChildFragmentManager(), job);
        viewPager.setAdapter(adapter);
        viewPager.removeOnPageChangeListener(pageChangeListener);
        viewPager.addOnPageChangeListener(pageChangeListener);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        String number = getResources().getString(R.string.job_number, job.getNumber());
        actionBar.setTitle(number);
    }

    @Override
    public boolean needsOptionsMenu() {
        return false;
    }

    private void invalidateFragmentMenus(int position) {
        for (int i = 0; i < adapter.getCount(); i++) {
            adapter.getItem(i).setHasOptionsMenu(i == position);
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
