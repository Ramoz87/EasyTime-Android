package com.example.paralect.easytime.main.expenses;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.main.expenses.expense.ExpenseEditorFragment;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 15.01.2018.
 */

public class ExpensesFragment extends AbsStickyFragment implements ExpenseCreatorDialog.Listener {

    private ExpensesAdapter adapter = new ExpensesAdapter(EasyTimeManager.getInstance().getDriving());

    public static ExpensesFragment newInstance() {
        return new ExpensesFragment();
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
    public void onCreate(ExpenseCreatorDialog dialog, String expenseName) {
        Toast.makeText(getContext(), String.format("created new expense %s", expenseName), Toast.LENGTH_SHORT).show();
        Expense expense = new Expense();
        expense.setName(expenseName);
        adapter.addExpense(expense);
    }
}
