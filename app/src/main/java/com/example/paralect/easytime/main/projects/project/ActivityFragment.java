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
import android.util.Pair;
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
import com.example.paralect.easytime.main.MainActivity;
import com.example.paralect.easytime.main.projects.project.jobexpenses.expenses.ExpensesFragment;
import com.example.paralect.easytime.main.projects.project.invoice.ProjectInvoiceFragment;
import com.example.paralect.easytime.main.projects.project.jobexpenses.materials.MaterialExpensesFragment;
import com.example.paralect.easytime.main.projects.project.jobexpenses.time.WorkTypeFragment;
import com.example.paralect.easytime.main.projects.project.objectsofproject.ObjectsOfProjectFragment;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.ProjectType;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.utils.DefAdapterDataObserver;
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

public class ActivityFragment extends BaseFragment
        implements DatePickerDialog.OnDateSetListener,
        FloatingActionMenu.OnMenuToggleListener,
        IDataView<Pair<Integer, List<Expense>>> {
    private static final String TAG = ActivityFragment.class.getSimpleName();

    @BindView(R.id.date) TextView dateTextView;
    @BindView(R.id.activityList) EmptyRecyclerView emptyRecyclerView;
    @BindView(R.id.emptyListPlaceholder) View emptyListPlaceholder;
    @BindView(R.id.overlay) View overlay;
    FloatingActionMenu fam;

    void addTime() {
        Fragment fragment;
        if (job.getProjectType() == ProjectType.Type.TYPE_PROJECT)
            fragment = ObjectsOfProjectFragment.newInstance((Project) job, WorkTypeFragment.TAG);
        else
            fragment = WorkTypeFragment.newInstance(job);

        getMainActivity().pushFragment(fragment);
    }

    void addMaterials() {
        Fragment fragment = MaterialExpensesFragment.newInstance(job);
        getMainActivity().getFragmentNavigator().pushFragment(fragment);
    }

    void addExpenses() {
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
    private int totalExpenseCountBefore = 0;
    private Job job;
    private RecyclerView.AdapterDataObserver observer = new DefAdapterDataObserver() {
        @Override
        public void onDataChanged() {
            invalidateOptionsMenu();
        }
    };

    public static ActivityFragment newInstance(Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(Job.TAG, job);
        ActivityFragment fragment = new ActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initJob() {
        if (job == null)
            job = Job.fromBundle(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_activity, parent, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "on create options menu");
        inflater.inflate(R.menu.menu_project_activity, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "on prepare options menu");
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.item_new).setVisible(totalExpenseCountBefore + adapter.getItemCount() > 0);
        menu.findItem(R.id.item_delete).setVisible(adapter.getItemCount() > 0);
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
                        .pushFragment(ProjectInvoiceFragment.newInstance(job, presenter.getDate()));
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
        Log.d(TAG, "on view created");
        ButterKnife.bind(this, view);
        initJob();
        initDate();
        initList();
        initFam();
        initOverlay();
        populate();
    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        String dateString = CalendarUtils.getDateString(calendar);
        dateTextView.setText(dateString);
    }

    private void initList() {
        emptyRecyclerView.setEmptyView(emptyListPlaceholder);
        emptyRecyclerView.setAdapter(adapter);
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        decoration.setDrawable(drawable);
        emptyRecyclerView.addItemDecoration(decoration);
    }

    private void populate() {
        presenter.setJob(job)
                .setDataView(this)
                .setupDateSearch(dateTextView)
                .requestData(new String[]{"", presenter.getDate()});
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        // open activity
        String dateString = CalendarUtils.getDateString(i, i1, i2);
        dateTextView.setText(dateString);
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.unregisterAdapterDataObserver(observer);
        fam.removeAllMenuButtons();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.registerAdapterDataObserver(observer);
        adapter.toggle(false);
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

    private void showOverlay() {
        getMainActivity().showOverlay();
    }

    // removing overlay
    private void hideOverlay() {
        getMainActivity().hideOverlay();
    }

    private void initFam() {
        Log.d(TAG, "initializing fam");
        fam = getFam();
        fam.setOnMenuToggleListener(this);
        Context context = getContext();
        Resources res = getResources();
        LayoutInflater inflater = LayoutInflater.from(context);
        final FloatingActionButton addTime = (FloatingActionButton) inflater.inflate(R.layout.fab, null, false);
        addTime.setImageResource(R.drawable.ic_time);
        addTime.setLabelText(res.getString(R.string.add_time));
        final FloatingActionButton addMaterials = (FloatingActionButton) inflater.inflate(R.layout.fab, null, false);
        addMaterials.setImageResource(R.drawable.ic_materials_blue);
        addMaterials.setLabelText(res.getString(R.string.add_material));
        final FloatingActionButton addExpenses = (FloatingActionButton) inflater.inflate(R.layout.fab, null, false);
        addExpenses.setImageResource(R.drawable.ic_expense);
        addExpenses.setLabelText(res.getString(R.string.add_expenses));
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == addTime) {
                    addTime();
                } else if (view == addMaterials) {
                    addMaterials();
                } else if (view == addExpenses) {
                    addExpenses();
                }
            }
        };
        addTime.setOnClickListener(listener);
        addMaterials.setOnClickListener(listener);
        addExpenses.setOnClickListener(listener);

        fam.addMenuButton(addTime);
        fam.addMenuButton(addMaterials);
        fam.addMenuButton(addExpenses);
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
    public void onDataReceived(Pair<Integer, List<Expense>> data) {
        List<Expense> expenses = data.second;
        Log.d(TAG, String.format("received %s expenses", expenses.size()));
        totalExpenseCountBefore = data.first - expenses.size();
        adapter.setData(expenses);
        invalidateOptionsMenu();
    }
}
