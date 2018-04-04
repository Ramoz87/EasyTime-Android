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
import com.example.paralect.easytime.manager.entitysource.ExpenseSource;
import com.example.paralect.easytime.model.Constants;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.views.KeypadEditorView;
import com.example.paralect.easytime.views.StrangeNumberInputView;
import com.example.paralect.easytime.model.Expense;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 19/01/2018.
 */

public class TimeExpensesFragment extends BaseFragment implements StrangeNumberInputView.OnChangeListener {

    public static final String TAG = TimeExpensesFragment.class.getSimpleName();
    private final ExpenseSource expenseSource = new ExpenseSource();

    private final int MAX_HOURS = 23;
    private final int MAX_MINS = 59;

    private KeypadEditorView keypadEditorView;
    @BindView(R.id.time_exp_hours_view) StrangeNumberInputView hoursView;
    @BindView(R.id.time_exp_minutes_view) StrangeNumberInputView minutesView;

    private Job job;
    private Type type;

    public static TimeExpensesFragment newInstance(@NonNull Job parcelable, Type type) {
        Bundle args = new Bundle();
        args.putParcelable(Job.TAG, parcelable);
        args.putParcelable(Type.TAG, type);
        TimeExpensesFragment fragment = new TimeExpensesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        job = Job.fromBundle(getArguments());
        type = Type.getType(getArguments());
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
        keypadEditorView = getKeypadEditor();
        keypadEditorView.expand();

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

    @Override
    public void onSelected(StrangeNumberInputView view, boolean isSelected) {

        keypadEditorView.setupEditText(view.getMainTextView());
        keypadEditorView.setOnKeypadItemClickListener(view.getKeypadItemClickListener());

        switch (view.getId()) {

            case R.id.time_exp_hours_view:
                view.setMaxInputNumber(MAX_HOURS);
                keypadEditorView.showNextButton();
                break;

            case R.id.time_exp_minutes_view:
                view.setMaxInputNumber(MAX_MINS);
                keypadEditorView.showDoneButton();
                break;
        }
    }

    @Override
    public void onEntered(StrangeNumberInputView view) {
        if (view.getId() == R.id.time_exp_hours_view){
            onCompleted();
        }
    }

    @Override
    public void onCompleted() {

        if (hoursView.isSelected()) {
            minutesView.requestFocus();
            keypadEditorView.showDoneButton();

        } else {
            Logger.d(TAG, "on completed");
            try {
                int hours = hoursView.getIntValue();
                int minutes = minutesView.getIntValue();
                
                if (hours == 0 && minutes == 0){
                    hoursView.errorAnimation();
                    minutesView.errorAnimation();

                } else {
                    Expense expense = Expense.createTimeExpense(job.getId(), type.getName(), hours, minutes);
                    expense = expenseSource.saveAndGetExpense(expense);
                    Logger.d(TAG, "SQLiteExpense created");

                    if (keypadEditorView.isExpanded())
                        keypadEditorView.collapse();
                    popToActivityFragment();
                }
            } catch (Throwable e) {
                Logger.e(e);
            }

        }
    }

    @Override
    public boolean onBackPressed() {
        keypadEditorView.collapse();
        getMainActivity().backForOneStep();
        return true;
    }

    private void popToActivityFragment() {
        getMainActivity().getFragmentNavigator().popToFragment(Constants.FRAGMENT_ACTIVITY_DEPTH);
    }
}
