package com.example.paralect.easytime.main.materials;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.FragmentNavigator;
import com.example.paralect.easytime.main.materials.chooser.MaterialChooserFragment;
import com.example.paralect.easytime.utils.ViewAnimationUtils;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 26.12.2017.
 */

public class MaterialsFragment extends BaseFragment {

    @BindView(R.id.list)
    EmptyRecyclerView list;

    @BindView(R.id.placeholder)
    View placeholder;

    private FloatingActionButton fab;

    void onFabClick(FloatingActionButton fab) {
        // Fragment fragment = MaterialChooserFragment.newInstance();
        // pushFragment(fragment);
        // navigator.pushFragment(fragment);
        FrameLayout overlayContainer = getMainActivity().getOverlayContainer();
        @ColorInt int color = ContextCompat.getColor(getContext(), R.color.materials_overlay_background);
        overlayContainer.setBackgroundColor(color);
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
        fab = getMainActivity().getFab();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClick((FloatingActionButton) view);
            }
        });
        // ViewAnimationUtils.expand(fab);
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

    public void drawBackground(Drawable drawable) {
        FrameLayout layout = getMainActivity().getOverlayContainer();
        layout.setBackground(drawable);
    }

    @Override
    public void onPause() {
        super.onPause();
        drawBackground(null);
        // fab.setVisibility(View.GONE);
        getMainActivity().hideFab(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().showFab(true);
    }
}
