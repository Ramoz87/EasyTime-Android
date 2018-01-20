package com.example.paralect.easytime.main.projects.project.jobexpenses.time;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.views.KeypadEditorView;
import com.example.paralect.easytime.views.StrangeNumberInputView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 19/01/2018.
 */

public class TimeExpensesFragment extends BaseFragment implements StrangeNumberInputView.OnChangeListener {

    public static final String TAG = TimeExpensesFragment.class.getSimpleName();
    public static final String ARG_JOB = "arg_job";

    private final int MAX_HOURS = 23;
    private final int MAX_MINS = 59;

    @BindView(R.id.keypad) KeypadEditorView keypadEditorView;
    @BindView(R.id.time_exp_hours_view) StrangeNumberInputView hoursView;
    @BindView(R.id.time_exp_minutes_view) StrangeNumberInputView minutesView;

    private Job job;

    public static TimeExpensesFragment newInstance(@NonNull Job parcelable) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_JOB, parcelable);
        TimeExpensesFragment fragment = new TimeExpensesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        job = getJobArg();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_expense, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        hoursView.setMainText("00");
        hoursView.setDetailsText(R.string.time_exp_hours);

        minutesView.setMainText("00");
        minutesView.setDetailsText(R.string.time_exp_minutes);

        hoursView.setOnSelectedChangeListener(this);
        minutesView.setOnSelectedChangeListener(this);

        hoursView.requestFocus();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        actionBar.setTitle(R.string.time_exp_title);
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    private Job getJobArg() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_JOB))
            return args.getParcelable(ARG_JOB);
        else return null;
    }

    @Override
    public void onSelected(StrangeNumberInputView view, boolean isSelected) {

        keypadEditorView.setupEditText(view.getMainTextView());
        keypadEditorView.setOnKeypadItemClickListener(view.getKeypadItemClickListener());

        switch (view.getId()) {

            case R.id.time_exp_hours_view:
                view.setMaxInputNumber(MAX_HOURS);
                break;

            case R.id.time_exp_minutes_view:
                view.setMaxInputNumber(MAX_MINS);
                break;
        }
    }

    @Override
    public void onCompleted() {
        try {
            int hours = hoursView.getIntValue();
            int minutes = minutesView.getIntValue();
            Expense expense = Expense.createTimeExpense(job, hours, minutes);
            expense = EasyTimeManager.getInstance().saveExpense(expense);
            Logger.d(TAG, "Expense created");
        } catch (Throwable e) {
            Logger.e(e);
        }
        getMainActivity().onBackPressed();
    }

}
