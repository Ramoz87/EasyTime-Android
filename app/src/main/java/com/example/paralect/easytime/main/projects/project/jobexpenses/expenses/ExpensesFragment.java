package com.example.paralect.easytime.main.projects.project.jobexpenses.expenses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.Logger;

import java.sql.SQLException;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 15.01.2018.
 */

public class ExpensesFragment extends AbsStickyFragment implements ExpenseCreatorDialog.Listener, IDataView<List<Expense>> {

    public static final String TAG = ExpensesFragment.class.getSimpleName();
    public static final String ARG_JOB = "arg_job";

    private ExpensesPresenter presenter = new ExpensesPresenter();
    private ExpensesAdapter adapter;

    public static ExpensesFragment newInstance(@NonNull Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_JOB, job);
        ExpensesFragment fragment = new ExpensesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Job getJobArg() {
        Bundle args = getArguments();
        if (args == null || !args.containsKey(ARG_JOB))
            return null;
        else return args.getParcelable(ARG_JOB);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Job job = getJobArg();
        String jobId = job.getJobId();
        adapter = new ExpensesAdapter(EasyTimeManager.getInstance().getDefaultExpenses(job));
        super.onViewCreated(view, savedInstanceState);
        presenter.setDataView(this)
                .requestData(new String[] { jobId });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        actionBar.setTitle(R.string.expenses);
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        return adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Expense expense = adapter.getItem(i);
        if (expense == null) {
            // show dialog to create new expense
            showCreatorDialog();
        } else {
            Fragment fragment = ExpenseEditorFragment.newInstance(expense);
            getMainActivity().getFragmentNavigator().pushFragment(fragment);
        }
    }

    private void showCreatorDialog() {
        ExpenseCreatorDialog dialog = new ExpenseCreatorDialog(getContext(), this);
        dialog.show();
    }

    @Override
    public void onCreateNewExpenseTemplate(ExpenseCreatorDialog dialog, String expenseName) {
        Toast.makeText(getContext(), String.format("created new expense %s", expenseName), Toast.LENGTH_SHORT).show();
        Expense expense = new Expense();
        expense.setName(expenseName);
        expense.setType(Expense.Type.OTHER);
        Job job = getJobArg();
        expense.setJobId(job.getJobId());
        ExpenseEditorFragment fragment = ExpenseEditorFragment.newInstance(expense);
        getMainActivity().getFragmentNavigator().pushFragment(fragment);
    }

    @Override
    public void onDataReceived(List<Expense> expenses) {
        Log.d(TAG, "on data received");
        adapter.setOtherExpenses(expenses);
    }
}
