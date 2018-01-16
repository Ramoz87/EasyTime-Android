package com.example.paralect.easytime.main.expenses.expense;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.MainActivity;
import com.example.paralect.easytime.main.expenses.ExpensesFragment;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.views.KeypadEditorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 15.01.2018.
 */

public class ExpenseEditorFragment extends BaseFragment implements KeypadEditorView.OnCompletionListener {
    private static final String TAG = ExpenseEditorFragment.class.getSimpleName();

    public static final String ARG_EXPENSE = "arg_expense";

    @BindView(R.id.keypad) KeypadEditorView keypadEditorView;
    @BindView(R.id.expenseName) TextView expenseName;
    @BindView(R.id.expenseCount) EditText expenseCount;

    private Expense expense;

    public static ExpenseEditorFragment newInstance(@NonNull Expense expense) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_EXPENSE, expense);
        ExpenseEditorFragment fragment = new ExpenseEditorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Expense getExpenseArg() {
        Bundle args = getArguments();
        if (args == null || !args.containsKey(ARG_EXPENSE))
            return null;
        else return args.getParcelable(ARG_EXPENSE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expense_editor, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        expense = getExpenseArg();
        int value = expense.getValue();
        expenseName.setText(expense.getName());

        expenseCount.setRawInputType(InputType.TYPE_CLASS_TEXT);
        expenseCount.setTextIsSelectable(true);
        expenseCount.requestFocus();
        if (value != 0) {
            String text = String.valueOf(value);
            expenseCount.setText(text);
            expenseCount.setSelection(text.length());
        }

        keypadEditorView.setOnCompletionListener(this);
        keypadEditorView.setupEditText(expenseCount);
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
    public void onCompletion(String result) {
        String message = "completed";
        Log.d(TAG, message);
        // Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        if (result.isEmpty()) {
            expense.setValue(0);
        } else {
            expense.setValue(Integer.valueOf(result));
        }
        EasyTimeManager.getInstance().updateExpense(expense);
        getMainActivity().onBackPressed();
    }
}
