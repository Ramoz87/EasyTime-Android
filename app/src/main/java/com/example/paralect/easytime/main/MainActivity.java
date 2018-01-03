package com.example.paralect.easytime.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.paralect.easytime.ContextUtils;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.base.OnBackPressListener;
import com.example.paralect.easytime.main.customers.CustomersFragment;
import com.example.paralect.easytime.main.materials.MaterialsFragment;
import com.example.paralect.easytime.main.projects.ProjectListFragment;
import com.example.paralect.easytime.main.settings.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 26.12.2017.
 */

public class MainActivity extends AppCompatActivity
        implements AHBottomNavigation.OnNavigationPositionListener, AHBottomNavigation.OnTabSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, MainActivity.class);
    }

    @BindView(R.id.navigationView)
    AHBottomNavigation bottomNavigation;

    private ProjectListFragment projectsFragment;
    private MaterialsFragment materialsFragment;
    private CustomersFragment customersFragment;
    private SettingsFragment settingsFragment;

    private OnBackPressListener onBackPressListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragmentManager();
        initNavigationView();
        iniActionBar();
        initProjectsFragment();
        pushFragment(projectsFragment);
    }

    private void initNavigationView() {
        Log.d(TAG, "initializing navigation view");
        AHBottomNavigationItem projects = new AHBottomNavigationItem(R.string.nav_projects, R.drawable.ic_projects, R.color.blue);
        AHBottomNavigationItem materials = new AHBottomNavigationItem(R.string.nav_materials, R.drawable.ic_materials, R.color.blue);
        AHBottomNavigationItem clients = new AHBottomNavigationItem(R.string.nav_clients, R.drawable.ic_clients, R.color.blue);
        AHBottomNavigationItem settings = new AHBottomNavigationItem(R.string.nav_settings, R.drawable.ic_settings, R.color.blue);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.addItem(projects);
        bottomNavigation.addItem(materials);
        bottomNavigation.addItem(clients);
        bottomNavigation.addItem(settings);

        bottomNavigation.setOnNavigationPositionListener(this);
        bottomNavigation.setOnTabSelectedListener(this);
        bottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.blue));
        int inactiveColor = ContextUtils.getAttrColor(this, R.attr.nav_inactive_color);
        bottomNavigation.setInactiveColor(inactiveColor);
    }

    @Override
    public void onPositionChange(int position) {
        Log.d(TAG, "on position change " + position);
        if (position == 0) {
            initProjectsFragment();
            pushFragment(projectsFragment);
        } else if (position == 1) {
            initMaterialsFragment();
            pushFragment(materialsFragment);
        } else if (position == 2) {
            initClientsFragment();
            pushFragment(customersFragment);
        } else if (position == 3) {
            initSettingsFragment();
            pushFragment(settingsFragment);
        }
    }

    private void initProjectsFragment() {
        if (projectsFragment == null)
            projectsFragment = ProjectListFragment.newInstance(null);
    }

    private void initMaterialsFragment() {
        if (materialsFragment == null)
            materialsFragment = MaterialsFragment.newInstance();
    }

    private void initClientsFragment() {
        if (customersFragment == null)
            customersFragment = CustomersFragment.newInstance();
    }

    private void initSettingsFragment() {
        if (settingsFragment == null)
            settingsFragment = SettingsFragment.newInstance();
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        Log.d(TAG, "on tab selected");
        onPositionChange(position);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void iniActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    public void pushFragment(Fragment fragment) {
        if (fragment instanceof OnBackPressListener) {
            onBackPressListener = (OnBackPressListener) fragment;
        } else {
            onBackPressListener = null;
        }

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(fragment.getClass().getSimpleName())
                .replace(R.id.container, fragment)
                .commit();
    }

    public void pushFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, fragment, tag)
                .commit();
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.container, fragment)
                .commit();
    }

    public void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.container, fragment, tag)
                .commit();
    }

    // works on Loli-Pop and higher
    public void setToolbarElevation(float elevation) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setElevation(elevation);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "on back pressed");
//        FragmentManager fm = getSupportFragmentManager();
//        fm.popBackStack();
        if (onBackPressListener != null) {
            boolean result = onBackPressListener.onBackPressed();
            if (!result) {
                onBackPressListener = null;
                // super.onBackPressed();
                finish();
            }
        } else {
            // super.onBackPressed();
            finish();
        }
    }

    private void initFragmentManager() {
        final FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
                if (backStackEntryCount == 0) {
                    finish();
                }
            }
        });
    }

}
