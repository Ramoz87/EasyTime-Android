package com.example.paralect.easytime.main.projects;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.paralect.easytime.main.FragmentNavigator;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.main.search.ISearchView;
import com.example.paralect.easytime.utils.ViewUtils;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.projects.project.ProjectFragment;
import com.example.paralect.easytime.model.Job;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 26.12.2017.
 */

public class ProjectsFragment extends AbsStickyFragment implements ISearchView<List<Job>> {
    private static final String TAG = ProjectsFragment.class.getSimpleName();

    private final ProjectsPresenter presenter = new ProjectsPresenter();
    private FragmentNavigator navigator;
    private ProjectStickyAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (FragmentNavigator) context;
    }

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        Log.d(TAG, "building sticky adapter");
        return adapter = new ProjectStickyAdapter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewUtils.setTitle(getActivity(), R.string.nav_projects);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        presenter.setupSearch(menu, R.id.item_search, this);
        presenter.requestData("");
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {

    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Job job = adapter.getItem(i);
        ProjectFragment fragment = ProjectFragment.newInstance(job);
        navigator.pushFragment(fragment);
    }

    @Override
    public void onDataReceived(List<Job> data) {
        adapter.setData(data);
    }
}
