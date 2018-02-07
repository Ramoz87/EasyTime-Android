package com.example.paralect.easytime.main.projects.project.jobexpenses.expenses;

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
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.MetricsUtils;
import com.example.paralect.easytime.utils.ViewAnimationUtils;
import com.example.paralect.easytime.views.KeypadEditorView;
import com.example.paralect.easytime.views.gallery.ExpenseFilesView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 15.01.2018.
 */

public class ExpenseEditorFragment extends BaseFragment implements KeypadEditorView.OnCompletionListener, ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = ExpenseEditorFragment.class.getSimpleName();

    public static final String ARG_EXPENSE = "arg_expense";

    private KeypadEditorView keypadEditorView;
    @BindView(R.id.expenseName) TextView expenseName;
    @BindView(R.id.expense_unit_text_view) TextView unitTextView;
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
        Log.d(TAG, "on view created");
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

            String unitName = Expense.getUnitName(mExpense.getType(), mExpense.getMaterial());
            unitTextView.setText(unitName);

            expenseName.setText(mExpense.getName());

            expenseCount.setRawInputType(InputType.TYPE_CLASS_TEXT);
            expenseCount.setTextIsSelectable(true);
            expenseCount.requestFocus();

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

        int value = result.isEmpty() ? 0 : Integer.valueOf(result);
        if (value == 0) {
            ViewAnimationUtils.shakeAnimation(unitTextView);
            ViewAnimationUtils.shakeAnimation(expenseCount);
            return;
        }
        
        Expense expense = Expense.reCreate(mExpense);
        expense.setValue(value);

        try {
            expense = EasyTimeManager.getInstance().saveExpense(expense);
            expenseFilesView.setupWithEntity(expense);
            Logger.d(TAG, "Expense created");
        } catch (Throwable e) {
            Logger.e(e);
        }
        keypadEditorView.collapse();
        getMainActivity().getFragmentNavigator().popToFragment(2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        expenseFilesView.checkFile();
    }

    @Override
    public boolean onBackPressed() {
        keypadEditorView.collapse();
        getMainActivity().backForOneStep();
        return true;
    }

    @Override
    public void onPause() {
        Log.d(TAG, "on pause");
        super.onPause();
        keypadEditorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "on resume");
        super.onResume();
        keypadEditorView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        keypadEditorView.expand();
    }

    private void onExpansionUpdate(View expandableView) {
        View root = getView();
        int navBarHeight = (int) MetricsUtils.convertDpToPixel(56);
        int height = expandableView.getMeasuredHeight() - navBarHeight /*for navigation bar at the bottom*/;
        if (height < 0) height = 0;
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) root.getLayoutParams();
        Log.d(TAG, "updated margin = " + height);
        int leftMargin = mlp.leftMargin;
        int topMargin = mlp.topMargin;
        int rightMargin = mlp.rightMargin;
        mlp.setMargins(leftMargin, topMargin, rightMargin, height);
    }

    @Override
    public void onGlobalLayout() {
        onExpansionUpdate(keypadEditorView);
    }
}
