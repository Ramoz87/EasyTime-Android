package com.example.paralect.easytime.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.paralect.easytime.R;

/**
 * Created by alexei on 27.12.2017.
 */

public abstract class FragmentWrapperActivity extends AppCompatActivity {
    private static final String TAG = FragmentWrapperActivity.class.getSimpleName();

    private static final String FRAGMENT_WRAPPED = "wrapped_fragment";

    private Fragment fragment;
    private Intent intent;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_wrapper);
        intent = getIntent();
        initFragment();
    }

    /**
     * initializes and adds fragment to this activity
     */
    private void initFragment() {
        fragment = createFragment(intent);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.bottom, fragment, FRAGMENT_WRAPPED)
                .commitNow();
    }

    public void showFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment)
                .commitNow();
    }

    public void hideFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(fragment)
                .commitNow();
    }

    /**
     * creates activity
     * @param intent intent of this activity
     * @return fragment
     */
    public abstract Fragment createFragment(Intent intent);

    @CallSuper
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Fragment newFragment = createFragment(intent);
        recreate(newFragment);
    }

    /**
     * just replaces old fragment for new one
     * @param newFragment new fragment
     */
    protected void recreate(Fragment newFragment) {
        if (fragment != null) { // check if container has fragment already
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.bottom, newFragment, FRAGMENT_WRAPPED)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.bottom, newFragment, FRAGMENT_WRAPPED)
                    .commit();
        }
        fragment = newFragment;
    }
}
