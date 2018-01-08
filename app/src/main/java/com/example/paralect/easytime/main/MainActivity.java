package com.example.paralect.easytime.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.customers.CustomersFragment;
import com.example.paralect.easytime.main.materials.MaterialsFragment;
import com.example.paralect.easytime.main.projects.ProjectsFragment;
import com.example.paralect.easytime.main.settings.SettingsFragment;
import com.example.paralect.easytime.utils.ViewUtils;
import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavSwitchController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 26.12.2017.
 */

public class MainActivity extends AppCompatActivity implements FragmentNavigator,
        FragNavController.TransactionListener,
        FragNavController.RootFragmentListener {

    private final int INDEX_PROJECTS = FragNavController.TAB1;
    private final int INDEX_MATERIALS = FragNavController.TAB2;
    private final int INDEX_CLIENTS = FragNavController.TAB3;
    private final int INDEX_SETTINGS = FragNavController.TAB4;

    private FragNavController mNavController;

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, MainActivity.class);
    }

    @BindView(R.id.navigationView) BottomNavigationView bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initNavigationView(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNavController.popFragment();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!mNavController.popFragment())
            super.onBackPressed();
    }

    // works on Loli-Pop and higher
    public void setToolbarElevation(float elevation) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setElevation(elevation);
    }

    // region Setup Navigation
    private void initNavigationView(Bundle savedInstanceState) {
        ViewUtils.disableShiftMode(bottomBar);
        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .transactionListener(this)
                .rootFragmentListener(this, 4)
                .popStrategy(FragNavTabHistoryController.UNIQUE_TAB_HISTORY)
                .switchController(new FragNavSwitchController() {
                    @Override
                    public void switchTab(int index, FragNavTransactionOptions transactionOptions) {
                        bottomBar.setSelectedItemId(getSelectedItemId(index));
                    }
                })
                .build();

        final FragNavTransactionOptions options = FragNavTransactionOptions.newBuilder()
                .customAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
                        R.anim.slide_in_right, R.anim.slide_out_right)
                .build();

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_projects:
                        mNavController.switchTab(INDEX_PROJECTS, options);
                        break;
                    case R.id.nav_materials:
                        mNavController.switchTab(INDEX_MATERIALS, options);
                        break;
                    case R.id.nav_clients:
                        mNavController.switchTab(INDEX_CLIENTS, options);
                        break;
                    case R.id.nav_settings:
                        mNavController.switchTab(INDEX_SETTINGS, options);
                        break;
                }
                return true;
            }
        });

        bottomBar.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                mNavController.clearStack();
            }
        });
    }

    private int getSelectedItemId(int index) {
        int itemId = R.id.nav_projects;
        switch (index) {
            case INDEX_PROJECTS:
                itemId = R.id.nav_projects;
                break;
            case INDEX_MATERIALS:
                itemId = R.id.nav_materials;
                break;
            case INDEX_CLIENTS:
                itemId = R.id.nav_clients;
                break;
            case INDEX_SETTINGS:
                itemId = R.id.nav_settings;
                break;
        }
        return itemId;
    }
    // endregion

    // region Listeners
    // region TransactionListener
    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }
    // endregion

    // region RootFragmentListener
    @Override
    public Fragment getRootFragment(int index) {
        Fragment fragment = null;
        switch (index) {
            case INDEX_PROJECTS:
                fragment = ProjectsFragment.newInstance();
                break;
            case INDEX_MATERIALS:
                fragment = MaterialsFragment.newInstance();
                break;
            case INDEX_CLIENTS:
                fragment = CustomersFragment.newInstance();
                break;
            case INDEX_SETTINGS:
                fragment = SettingsFragment.newInstance();
                break;
            default:
                fragment = ProjectsFragment.newInstance();

        }
        return fragment;
    }
    // endregion

    // region FragmentNavigator
    @Override
    public void pushFragment(Fragment fragment) {
        mNavController.pushFragment(fragment);
    }
    // endregion
    // endregion

}
