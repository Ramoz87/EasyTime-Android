package com.example.paralect.easytime.main.projects.project.jobexpenses.expenses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.views.KeypadEditorView;
import com.example.paralect.easytime.views.gallery.ExpenseFilesView;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 15.01.2018.
 */

public class ExpenseEditorFragment extends BaseFragment implements KeypadEditorView.OnCompletionListener {
    private static final String TAG = ExpenseEditorFragment.class.getSimpleName();

    public static final String ARG_EXPENSE = "arg_expense";

    private KeypadEditorView keypadEditorView;
    @BindView(R.id.expenseName) TextView expenseName;
    @BindView(R.id.expenseCount) EditText expenseCount;
    @BindView(R.id.expense_file_view) ExpenseFilesView expenseFilesView;

    private Expense mExpense;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expense_editor, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        keypadEditorView = getKeypadEditor();
        mExpense = getExpenseArg();

        if (mExpense == null) {
            backForOneStep();

        } else {
            long value = mExpense.getValue();
            expenseName.setText(mExpense.getName());

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
    public void onCompletion(KeypadEditorView keypadEditorView, String result) {
        Logger.d(TAG, "completed");

        Expense expense = Expense.reCreate(mExpense);
        int value = result.isEmpty() ? 0 : Integer.valueOf(result);
        expense.setValue(value);

        try {
            expense = EasyTimeManager.getInstance().saveExpense(expense);
            expenseFilesView.setupWithEntity(expense);
            Logger.d(TAG, "Expense created");
        } catch (Throwable e) {
            Logger.e(e);
        }
        keypadEditorView.collapse();
        backForOneStep();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        expenseFilesView.checkFile();
    }
}
