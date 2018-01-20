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
import android.widget.EditText;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.views.KeypadEditorView;
import com.example.paralect.easytime.views.KeypadView;
import com.example.paralect.easytime.views.StrangeInputView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 19/01/2018.
 */

public class TimeExpensesFragment extends BaseFragment implements StrangeInputView.OnSelectedChangeListener, KeypadView.OnKeypadItemClickListener {

    public static final String ARG_JOB = "arg_job";

    @BindView(R.id.keypad) KeypadEditorView keypadEditorView;
    @BindView(R.id.time_exp_hours_view) StrangeInputView hoursView;
    @BindView(R.id.time_exp_minutes_view) StrangeInputView minutesView;

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

        keypadEditorView.setOnKeypadItemClickListener(this);

        hoursView.setMainText("00");
        hoursView.setDetailsText("Hours");

        minutesView.setMainText("00");
        minutesView.setDetailsText("Minutes");

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
    public void onSelected(StrangeInputView view, boolean isSelected) {
        keypadEditorView.setupEditText(view.getMainTextView());
    }

    // region KeypadView
    @Override
    public void onNextClick() {

    }

    @Override
    public void onDeleteClick() {
        EditText editText = hoursView.getMainTextView();
        String currentText = editText.getText().toString();

        if (currentText.length() == 2) {
            int first = Character.getNumericValue(currentText.charAt(0));
            int second = Character.getNumericValue(currentText.charAt(1));

            String result = currentText;
            if (first != 0 && second != 0) {
                result = first + "" + 0;
            } else if (first != 0 && second == 0) {
                result = "00";
            } else if (first == 0 && second != 0) {
                result = first + "" + "0";
            }

            editText.setText(result);
        }
    }

    @Override
    public void onNumberClick(int number) {
        EditText editText = hoursView.getMainTextView();
        String currentText = editText.getText().toString();

        if (currentText.length() == 2) {
            int first = Character.getNumericValue(currentText.charAt(0));
            int second = Character.getNumericValue(currentText.charAt(1));

            String result = currentText;
            if (first == 0 && second == 0) {
                result = first + "" + number;
            } else if (first == 0 && second != 0) {
                result = second + "" + number;
            } else if (first != 0 && second == 0) {
                result = first + "" + number;
            }

            editText.setText(result);
        }
    }
    // endregion

}
