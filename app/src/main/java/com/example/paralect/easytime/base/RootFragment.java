package com.example.paralect.easytime.base;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.paralect.easytime.R;

/**
 * Created by alexei on 29.12.2017.
 */

public abstract class RootFragment extends Fragment implements OnBackPressListener {
    private static final String TAG = RootFragment.class.getSimpleName();

    public abstract @IdRes int getRootViewId();

    @Override
    public boolean onBackPressed() {
        // return new BasePressImpl(this).onBackPressed();
        return popFragment();
    }

    public void pushFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction()
                .addToBackStack(null)
                //.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(getRootViewId(), fragment).commit();
    }

    private boolean popFragment() {
        FragmentManager fm = getChildFragmentManager();
        int backStackEntryCount = fm.getBackStackEntryCount();
        Log.d(TAG, "backStackEntryCount = " + backStackEntryCount);
        if (backStackEntryCount == 0) return false; // there is one fragment left only
        // else
        fm.popBackStack();
        return true;
    }
}
