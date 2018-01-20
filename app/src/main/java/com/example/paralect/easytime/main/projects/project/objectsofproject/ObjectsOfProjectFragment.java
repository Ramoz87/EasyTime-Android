package com.example.paralect.easytime.main.projects.project.objectsofproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.main.projects.project.jobexpenses.expenses.ExpensesFragment;
import com.example.paralect.easytime.main.projects.project.jobexpenses.time.TimeExpensesFragment;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.utils.TextUtil;

import java.util.List;
import java.util.SortedMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 17.01.2018.
 */

public class ObjectsOfProjectFragment extends AbsStickyFragment implements IDataView<SortedMap<Character, List<Object>>> {

    public static final String TAG = ObjectsOfProjectFragment.class.getSimpleName();
    public static final String ARG_PROJECT = "arg_project";

    private ObjectsOfProjectPresenter presenter = new ObjectsOfProjectPresenter();
    private ObjectsOfProjectAdapter adapter = new ObjectsOfProjectAdapter();
    private Project project;

    public static ObjectsOfProjectFragment newInstance(@NonNull Project project, @NonNull String fragmentName) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_PROJECT, project);
        args.putString(FRAGMENT_KEY, fragmentName);
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

    private String getFragmentName() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(FRAGMENT_KEY)) {
            return args.getString(FRAGMENT_KEY);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Object object = adapter.getItem(i);
        String fragmentName = getFragmentName();

        if (TextUtil.isNotEmpty(fragmentName)) {
            Fragment fragment = null;

            if (fragmentName.equalsIgnoreCase(TimeExpensesFragment.TAG))
                fragment = TimeExpensesFragment.newInstance(object);

            else if (fragmentName.equalsIgnoreCase(ExpensesFragment.TAG))
                fragment = ExpensesFragment.newInstance(object);

            if (fragment != null)
                getMainActivity().getFragmentNavigator().pushFragment(fragment);
        }
    }
}
