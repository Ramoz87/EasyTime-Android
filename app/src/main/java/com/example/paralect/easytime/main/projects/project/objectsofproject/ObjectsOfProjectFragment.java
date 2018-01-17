package com.example.paralect.easytime.main.projects.project.objectsofproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.utils.Sorter;

import java.util.List;
import java.util.SortedMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 17.01.2018.
 */

public class ObjectsOfProjectFragment extends AbsStickyFragment implements IDataView<SortedMap<Character, List<Object>>> {

    public static final String ARG_PROJECT = "arg_project";

    private ObjectsOfProjectPresenter presenter = new ObjectsOfProjectPresenter();
    private ObjectsOfProjectAdapter adapter = new ObjectsOfProjectAdapter();
    private Project project;

    public static ObjectsOfProjectFragment newInstance(@NonNull Project project) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_PROJECT, project);
        ObjectsOfProjectFragment fragment = new ObjectsOfProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Project getProjectArg() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_PROJECT)) {
            return args.getParcelable(ARG_PROJECT);
        } else return null;
    }

    private void initProject() {
        if (project == null) {
            project = getProjectArg();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProject();
        presenter.setDataView(this)
                .requestData(project.getObjectIds());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        actionBar.setTitle(R.string.objects);
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
    public void onDataReceived(SortedMap<Character, List<Object>> characterListSortedMap) {
        adapter.setData(characterListSortedMap);
    }
}
