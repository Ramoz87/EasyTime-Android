package com.example.paralect.easytime.main;

import android.support.v4.app.Fragment;

/**
 * Created by alexei on 04.01.2018.
 */

public interface FragmentNavigator {
    void pushFragment(Fragment newFragment);

    void popFragments(int depth);

    void popToFragment(int depth);

}
