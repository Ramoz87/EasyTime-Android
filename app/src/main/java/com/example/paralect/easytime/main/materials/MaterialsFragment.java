package com.example.paralect.easytime.main.materials;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.paralect.easytime.main.FragmentNavigator;
import com.example.paralect.easytime.base.RootFragment;
import com.example.paralect.easytime.main.materials.chooser.MaterialChooserFragment;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 26.12.2017.
 */

public class MaterialsFragment extends RootFragment {

    @BindView(R.id.list)
    EmptyRecyclerView list;

    @BindView(R.id.placeholder)
    View placeholder;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @OnClick(R.id.fab)
    void onFabClick(View view) {
        Fragment fragment = MaterialChooserFragment.newInstance();
        // pushFragment(fragment);
        navigator.pushFragment(fragment);
    }

    private FragmentNavigator navigator;

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
        View view = inflater.inflate(R.layout.fragment_materials, parent, false);
        setHasOptionsMenu(true);
        return view;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        list.setEmptyView(placeholder);

        if (getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            initActionBar(actionBar);
        }
    }

    private void initActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setTitle(R.string.nav_materials);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.materials, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getRootViewId() {
        return R.id.bottom;
    }
}
