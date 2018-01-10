package com.example.paralect.easytime.main.projects.project;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.views.EmptyRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 27.12.2017.
 */

public class ActivityFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.date)
    TextView date;

    @BindView(R.id.activityList)
    EmptyRecyclerView emptyRecyclerView;

    @BindView(R.id.emptyListPlaceholder)
    View emptyListPlaceholder;

    @OnClick(R.id.date)
    void onChooseDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, 2017, 11, 30);
        datePickerDialog.show();
    }

    public static ActivityFragment newInstance() {
        return new ActivityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_activity, parent, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {

    }

    @Override
    public boolean needsOptionsMenu() {
        return false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        emptyRecyclerView.setEmptyView(emptyListPlaceholder);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        // open activity
        String dateString = CalendarUtils.getDateString(i, i1, i2);
        date.setText(dateString);
    }
}
