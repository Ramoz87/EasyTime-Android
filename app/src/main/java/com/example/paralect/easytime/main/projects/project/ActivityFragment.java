package com.example.paralect.easytime.main.projects.project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.main.projects.project.jobexpenses.expenses.ExpensesFragment;
import com.example.paralect.easytime.main.projects.project.details.ProjectDetailsFragment;
import com.example.paralect.easytime.main.projects.project.jobexpenses.materials.MaterialExpensesFragment;
import com.example.paralect.easytime.main.projects.project.jobexpenses.time.TimeExpensesFragment;
import com.example.paralect.easytime.main.projects.project.objectsofproject.ObjectsOfProjectFragment;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.ProjectType;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.utils.anim.AnimUtils;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 27.12.2017.
 */

public class ActivityFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, FloatingActionMenu.OnMenuToggleListener, IDataView<List<Expense>> {
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
        Job job = getJobArg();
        Fragment fragment;
        if (job.getProjectType() == ProjectType.Type.TYPE_PROJECT)
            fragment = ObjectsOfProjectFragment.newInstance((Project) job, TimeExpensesFragment.TAG);
        else
            fragment = TimeExpensesFragment.newInstance(job);

        getMainActivity().pushFragment(fragment);
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
            fragment = ObjectsOfProjectFragment.newInstance((Project) job, ExpensesFragment.TAG);
        } else {
            fragment = ExpensesFragment.newInstance(job);
        }
        getMainActivity().pushFragment(fragment);
    }

    private ActivityPresenter presenter = new ActivityPresenter();
    private ActivityAdapter adapter = new ActivityAdapter();
    private Job job;

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

    private void initJob() {
        if (job == null)
            job = getJobArg();
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
            case R.id.item_delete: {
                Log.d(TAG, "toggled to delete items");
                adapter.toggle();
                return true;
            }
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initJob();
        initDate();
        initList();
        initFam();
        initOverlay();
        initAnimations();
        populate();
    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        String dateString = CalendarUtils.getDateString(calendar);
        date.setText(dateString);
    }

    private void initList() {
        emptyRecyclerView.setEmptyView(emptyListPlaceholder);
        emptyRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        emptyRecyclerView.setLayoutManager(lm);

        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.rect_divider);
        decoration.setDrawable(drawable);
        emptyRecyclerView.addItemDecoration(decoration);
    }

    private void populate() {
        presenter.setDataView(this)
                .requestData(new String[]{job.getJobId()});
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

    @Override
    public void onDataReceived(List<Expense> expenses) {
        Log.d(TAG, String.format("received %s expenses", expenses.size()));
        adapter.setData(expenses);
    }
}
