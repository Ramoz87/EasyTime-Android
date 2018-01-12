package com.example.paralect.easytime.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.customers.CustomersFragment;
import com.example.paralect.easytime.main.materials.MaterialsFragment;
import com.example.paralect.easytime.main.projects.ProjectsFragment;
import com.example.paralect.easytime.main.settings.SettingsFragment;
import com.example.paralect.easytime.utils.ViewUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
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

    private FabAnimListener fabIncAnimListener;
    private FabAnimListener fabDecAnimListener;
    private Animation incAnim;
    private Animation decAnim;

    @BindView(R.id.navigationView) BottomNavigationView bottomBar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.appbar) AppBarLayout appBarLayout;
    @BindView(R.id.top_shadow_view) View mainTopShadowView;
    @BindView(R.id.overlayContainer) FrameLayout overlayContainer;
    @BindView(R.id.fab) FloatingActionButton fab;

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ViewUtils.disableToolbarAnimation(toolbar);

        initNavigationView(savedInstanceState);
        initAnim();
        hideFab(false);
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

    public TextView getTitleTextView() {
        return ViewUtils.getToolbarTextView(toolbar);
    }

    public void showMainTopShadow(boolean show) {
        mainTopShadowView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public FrameLayout getOverlayContainer() {
        return overlayContainer;
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public void showFab(boolean animate) {
        if (animate) {
            fab.startAnimation(incAnim);
        } else {
            fab.setVisibility(View.VISIBLE);
        }
    }

    public void hideFab(boolean animate) {
        if (animate) {
            fab.startAnimation(decAnim);
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    private void initAnim() {
        fabIncAnimListener = new FabAnimListener(fab, true);
        fabDecAnimListener = new FabAnimListener(fab, false);
        incAnim = AnimationUtils.loadAnimation(this, R.anim.fab_increase);
        decAnim = AnimationUtils.loadAnimation(this, R.anim.fab_decrease);
        incAnim.setAnimationListener(fabIncAnimListener);
        decAnim.setAnimationListener(fabDecAnimListener);
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
                .customAnimations(R.anim.fade_in, R.anim.fade_out)
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

    private static class FabAnimListener implements Animation.AnimationListener {

        private FloatingActionButton fab;
        private boolean forInc = true; // for decAnim in another case

        private FabAnimListener(FloatingActionButton fab, boolean forInc) {
            this.fab = fab;
            this.forInc = forInc;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            fab.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            fab.setVisibility(forInc ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
