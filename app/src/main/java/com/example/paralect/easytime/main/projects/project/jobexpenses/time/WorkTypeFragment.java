package com.example.paralect.easytime.main.projects.project.jobexpenses.time;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.views.adapter.RecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 26/01/2018.
 */

public class WorkTypeFragment extends BaseFragment implements IDataView<List<Type>>{

    public static final String TAG = WorkTypeFragment.class.getSimpleName();

    @BindView(R.id.work_type_recycler_view) RecyclerView recyclerView;

    private WorkTypePresenter presenter = new WorkTypePresenter();
    private WorkTypeAdapter adapter = new WorkTypeAdapter();
    private Job job;

    public static WorkTypeFragment newInstance(@NonNull Job parcelable) {
        Bundle args = new Bundle();
        args.putParcelable(Job.TAG, parcelable);
        WorkTypeFragment fragment = new WorkTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        job = Job.getJob(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_work_types, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        decoration.setDrawable(drawable);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(decoration);
        adapter.setOnItemClickListener(new RecyclerViewAdapter.SimpleOnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Type type = adapter.getItem(position);
                TimeExpensesFragment fragment = TimeExpensesFragment.newInstance(job, type);
                getMainActivity().pushFragment(fragment);
            }
        });

        presenter.setDataView(this).requestData(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        actionBar.setTitle(R.string.time_exp_choose_work);
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public void onDataReceived(List<Type> types) {
        adapter.setTypes(types);
    }
}
