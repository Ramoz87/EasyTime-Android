package com.example.paralect.easytime.views;

/**
 * Created by Oleg Tarashkevich on 12.11.15.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.WeakHashMap;

public abstract class BaseFragmentPagerAdapter<F extends Fragment> extends FragmentPagerAdapter {

    private WeakHashMap<Integer, F> mFragments = new WeakHashMap<>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public F getItem(int position) {
        F fragment = getFragment(position);
        mFragments.put(position, fragment);
        return onCreateFragment(position);
    }

    public abstract F onCreateFragment(int position);

    public WeakHashMap<Integer, F> getFragments() {
        return mFragments;
    }

    public F getFragment(int position) {
        F fragment = null;
        if (mFragments.containsKey(position))
            fragment = mFragments.get(position);
        return fragment;
    }
}