package com.example.paralect.easytime.main.projects.project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.projects.project.jobexpenses.expenses.ExpensesFragment;
import com.example.paralect.easytime.main.projects.project.details.ProjectDetailsFragment;
import com.example.paralect.easytime.main.projects.project.jobexpenses.materials.MaterialExpensesFragment;
import com.example.paralect.easytime.main.projects.project.objectsofproject.ObjectsOfProjectFragment;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.ProjectType;
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

    public static final String ARG_JOB = "arg_job";

    @BindView(R.id.date) TextView date;
    @BindView(R.id.activityList) EmptyRecyclerView emptyRecyclerView;
    @BindView(R.id.emptyListPlaceholder) View emptyListPlaceholder;
    @BindView(R.id.overlay) View overlay;
    @BindView(R.id.fam) FloatingActionMenu fam;
    @BindView(R.id.addTime) FloatingActionButton addTime;
    @BindView(R.id.addMaterials) FloatingActionButton addMaterials;
    @BindView(R.id.addExpenses) FloatingActionButton addExpenses;

    @OnClick(R.id.addTime)
    void addTime(FloatingActionButton fab) {

    }

    @OnClick(R.id.addMaterials)
    void addMaterials(FloatingActionButton fab) {
        Fragment fragment = MaterialExpensesFragment.newInstance(getJobArg());
        getMainActivity().getFragmentNavigator().pushFragment(fragment);
    }

    @OnClick(R.id.addExpenses)
    void addExpenses(FloatingActionButton fab) {
        Job job = getJobArg();
        Fragment fragment;
        if (job.getProjectType() == ProjectType.Type.TYPE_PROJECT) {
            fragment = ObjectsOfProjectFragment.newInstance((Project) job);
        } else {
            fragment = ExpensesFragment.newInstance(job);
        }
        getMainActivity().pushFragment(fragment);
    }

    private Animation fadeIn;
    private Animation fadeOut;

    @OnClick(R.id.date)
    void onChooseDate(View view) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getContext(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public static ActivityFragment newInstance(Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_JOB, job);
        ActivityFragment fragment = new ActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Job getJobArg() {
        Bundle args = getArguments();
        if (args == null || !args.containsKey(ARG_JOB)) return null;
        else return args.getParcelable(ARG_JOB);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_activity, parent, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_project_activity, menu);
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {

    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_new:
                getMainActivity().getFragmentNavigator()
                        .pushFragment(ProjectDetailsFragment.newInstance(getJobArg()));
                return true;
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        emptyRecyclerView.setEmptyView(emptyListPlaceholder);
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
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initOverlay() {
        overlay.setVisibility(View.GONE);
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
        fam.setOnMenuToggleListener(this);
    }

    private void initAnimations() {
        int duration = 100;
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        fadeIn.setAnimationListener(AnimUtils.newAppearingAnimListener(overlay));
        fadeOut.setAnimationListener(AnimUtils.newDisappearingAnimListener(overlay));
        fadeIn.setDuration(duration);
        fadeOut.setDuration(duration);
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
