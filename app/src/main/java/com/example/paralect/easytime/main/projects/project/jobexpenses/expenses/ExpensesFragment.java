package com.example.paralect.easytime.main.projects.project.jobexpenses.expenses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
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

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 15.01.2018.
 */

public class ExpensesFragment extends AbsStickyFragment implements ExpenseCreatorDialog.Listener,
        IDataView<ExpensesPresenter.ExpensesContainer> {

    public static final String TAG = ExpensesFragment.class.getSimpleName();

    private ExpensesPresenter presenter = new ExpensesPresenter();
    private ExpensesAdapter adapter = new ExpensesAdapter();
    private Job job;

    public static ExpensesFragment newInstance(@NonNull Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(Job.TAG, job);
        ExpensesFragment fragment = new ExpensesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        job = Job.fromBundle(getArguments());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        presenter.setJobId(job.getJobId())
                .setDataView(this)
                .setupQuerySearch((SearchView) menu.findItem(R.id.item_search).getActionView())
                .requestData(new String[] {""});
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
        if (expense.getName().equalsIgnoreCase(getString(R.string.other_expenses))) {
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
        expense.setJobId(job.getJobId());
        ExpenseEditorFragment fragment = ExpenseEditorFragment.newInstance(expense);
        getMainActivity().getFragmentNavigator().pushFragment(fragment);
    }

    @Override
    public void onDataReceived(ExpensesPresenter.ExpensesContainer exp) {
        Log.d(TAG, "on data received");
        adapter.setExpenses(exp.getDefaultExpenses(), exp.getOtherExpenses());
    }
}
