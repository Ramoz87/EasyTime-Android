package com.example.paralect.easytime.main.materials;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.FragmentNavigator;
import com.example.paralect.easytime.main.materials.chooser.MaterialChooserFragment;
import com.example.paralect.easytime.utils.anim.AnimUtils;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 26.12.2017.
 */

public class MaterialsFragment extends BaseFragment {
    private static final String TAG = MaterialsFragment.class.getSimpleName();

    @BindView(R.id.list)
    EmptyRecyclerView list;

    @BindView(R.id.placeholder)
    View placeholder;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private FragmentNavigator navigator;

    @OnClick(R.id.fab)
    void onFabClick(FloatingActionButton fab) {
        Log.d(TAG, "on fab click");
        Fragment chooser = MaterialChooserFragment.newInstance();
        navigator.pushFragment(chooser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (FragmentNavigator) context;
    }

    public static MaterialsFragment newInstance() {
        return new MaterialsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_materials, parent, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        list.setEmptyView(placeholder);
    }

    private void initActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setTitle(R.string.nav_materials);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        initActionBar(actionBar);
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
