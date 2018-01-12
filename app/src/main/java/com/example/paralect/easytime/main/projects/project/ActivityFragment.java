package com.example.paralect.easytime.main.projects.project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.utils.anim.AnimUtils;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 27.12.2017.
 */

public class ActivityFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, FloatingActionMenu.OnMenuToggleListener {
    private static final String TAG = ActivityFragment.class.getSimpleName();

    @BindView(R.id.date)
    TextView date;

    @BindView(R.id.activityList)
    EmptyRecyclerView emptyRecyclerView;

    @BindView(R.id.emptyListPlaceholder)
    View emptyListPlaceholder;

    private View overlay;
    private FloatingActionMenu fam;

    private Animation fadeIn;
    private Animation fadeOut;

    @OnClick(R.id.date)
    void onChooseDate(View view) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getContext(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public static ActivityFragment newInstance() {
        return new ActivityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_activity, parent, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {

    }

    @Override
    public boolean needsOptionsMenu() {
        return false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        emptyRecyclerView.setEmptyView(emptyListPlaceholder);
        fam = getMainActivity().getFam();
        initFam();
        initOverlay();
        initAnimations();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        // open activity
        String dateString = CalendarUtils.getDateString(i, i1, i2);
        date.setText(dateString);
    }

    @Override
    public void onPause() {
        super.onPause();
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
