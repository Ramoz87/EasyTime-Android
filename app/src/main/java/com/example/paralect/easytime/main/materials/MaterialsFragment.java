package com.example.paralect.easytime.main.materials;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
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
import com.example.paralect.easytime.utils.anim.AnimUtils;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 26.12.2017.
 */

public class MaterialsFragment extends BaseFragment implements FloatingActionMenu.OnMenuToggleListener {
    private static final String TAG = MaterialsFragment.class.getSimpleName();

    @BindView(R.id.list)
    EmptyRecyclerView list;

    @BindView(R.id.placeholder)
    View placeholder;

    private View overlay;
    private FloatingActionMenu fam;

    private Animation fadeIn;
    private Animation fadeOut;

    void onFabClick(FloatingActionButton fab) {
        FrameLayout overlayContainer = getMainActivity().getOverlayContainer();
        overlayContainer.addView(overlay, 0);
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
        fam = getMainActivity().getFam();
        initFam();
        initOverlay();
        initAnimations();
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
        getMainActivity().getOverlayContainer().removeView(overlay);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().showFab(true);
    }

    private void initOverlay() {
        final FrameLayout container = getMainActivity().getOverlayContainer();
        overlay = LayoutInflater.from(getContext()).inflate(R.layout.materials_overlay, container, false);
        overlay.setVisibility(View.GONE);
        getMainActivity().getOverlayContainer().addView(overlay, 0);
        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hideOverlay();
                fam.close(true);
            }
        });
    }

    // adding overlay on front of app screen but under fam
    private void showOverlay() {
        overlay.startAnimation(fadeIn);
    }

    // removing overlay
    private void hideOverlay() {
        overlay.startAnimation(fadeOut);
    }

    private void initFam() {
        Log.d(TAG, "initializing fam");
        Context context = getContext();
        Resources res = getResources();
        LayoutInflater inflater = LayoutInflater.from(context);
        FloatingActionMenu fam = this.fam;
        getMainActivity().clearFam();

        int white = res.getColor(R.color.blue);
        Drawable time = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_time).getConstantState().newDrawable());
        Drawable materials = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_materials).getConstantState().newDrawable());
        Drawable expenses = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_expense).getConstantState().newDrawable());

        DrawableCompat.setTint(time.mutate(), white);
        DrawableCompat.setTint(materials.mutate(), white);
        DrawableCompat.setTint(expenses.mutate(), white);

        FloatingActionButton addTime = (FloatingActionButton) inflater.inflate(R.layout.include_fab, null, false);
        addTime.setImageDrawable(time);
        addTime.setLabelText(res.getString(R.string.add_time));
        fam.addMenuButton(addTime);

        FloatingActionButton addMaterial = (FloatingActionButton) inflater.inflate(R.layout.include_fab, null, false);
        addMaterial.setImageDrawable(materials);
        addMaterial.setLabelText(res.getString(R.string.add_material));
        fam.addMenuButton(addMaterial);

        FloatingActionButton addExpenses = (FloatingActionButton) inflater.inflate(R.layout.include_fab, null, false);
        addExpenses.setImageDrawable(expenses);
        addExpenses.setLabelText(res.getString(R.string.add_expenses));
        fam.addMenuButton(addExpenses);

        fam.setOnMenuToggleListener(this);
    }

    private void initAnimations() {
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        fadeIn.setAnimationListener(AnimUtils.newAppearingAnimListener(overlay));
        fadeOut.setAnimationListener(AnimUtils.newDisappearingAnimListener(overlay));
    }

    @Override
    public void onMenuToggle(boolean opened) {
        if (opened) {
            showOverlay();
        } else {
            hideOverlay();
        }
    }
}
