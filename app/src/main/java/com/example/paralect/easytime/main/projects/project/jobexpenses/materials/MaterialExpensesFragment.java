package com.example.paralect.easytime.main.projects.project.jobexpenses.materials;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.utils.VerticalDividerItemDecoration;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.views.KeypadEditorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 17.01.2018.
 */

public class MaterialExpensesFragment extends BaseFragment implements IDataView<List<Material>> {
    private static final String TAG = MaterialExpensesFragment.class.getSimpleName();

    public static final String ARG_JOB = "arg_job";

    @BindView(R.id.itemCount) TextView itemCount;
    @BindView(R.id.list) EmptyRecyclerView emptyRecyclerView;
    @BindView(R.id.keypad) KeypadEditorView keypadEditorView;

    private Job job;
    private MaterialExpensesAdapter adapter = new MaterialExpensesAdapter();
    private MaterialExpensesPresenter presenter = new MaterialExpensesPresenter();

    public static MaterialExpensesFragment newInstance(@NonNull Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_JOB, job);
        MaterialExpensesFragment fragment = new MaterialExpensesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Job getJobArg() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_JOB))
            return args.getParcelable(ARG_JOB);
        else return null;
    }

    private void initJob() {
        if (job == null)
            job = getJobArg();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_material_expenses, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        initJob();
        Resources res = getResources();
        itemCount.setText(res.getString(R.string.item_selected, 0));

        adapter.setKeypadEditorView(keypadEditorView);
        adapter.setJob(job);

        emptyRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        emptyRecyclerView.setLayoutManager(lm);
        int color = ContextCompat.getColor(getContext(), R.color.list_divider_color);
        int height = getResources().getInteger(R.integer.list_divider_height);
        RecyclerView.ItemDecoration decor = new VerticalDividerItemDecoration(color, height, 25);
        emptyRecyclerView.addItemDecoration(decor);

        hideKeypad(false);

        presenter.setDataView(this)
                .requestData(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {

    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public void onDataReceived(List<Material> materials) {
        adapter.setData(materials);
    }

    private void showKeypad(boolean animate) {
        keypadEditorView.expand(animate);
    }

    private void hideKeypad(boolean animate) {
        keypadEditorView.collapse(animate);
    }
}
