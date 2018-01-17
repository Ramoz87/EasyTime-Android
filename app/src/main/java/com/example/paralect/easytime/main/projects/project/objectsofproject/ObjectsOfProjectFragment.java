package com.example.paralect.easytime.main.projects.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.model.Project;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 17.01.2018.
 */

public class ObjectsOfProjectFragment extends AbsStickyFragment {

    public static final String ARG_PROJECT = "arg_project";

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
    public StickyListHeadersAdapter buildAdapter() {
        return null;
    }
}
