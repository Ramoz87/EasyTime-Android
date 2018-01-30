package com.example.paralect.easytime.main.projects;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.paralect.easytime.main.FragmentNavigator;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.main.MainActivity;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.projects.project.ProjectFragment;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.ViewUtils;

import java.util.Calendar;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 26.12.2017.
 */

public class ProjectsFragment extends AbsStickyFragment implements IDataView<List<Job>> {
    private static final String TAG = ProjectsFragment.class.getSimpleName();

    private final ProjectsPresenter presenter = new ProjectsPresenter();
    private ProjectStickyAdapter adapter = new ProjectStickyAdapter();
    private TextView title;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setDataView(this);
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        Log.d(TAG, "building sticky adapter");
        return adapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();

        // Set searchView width
        int[] displaySize = ViewUtils.displaySize(getContext());
        searchView.setMaxWidth((int) (displaySize[0] * 0.6f));
//        searchView.setMaxWidth(Integer.MAX_VALUE);

        presenter.setupQuerySearch(searchView);
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setTitle(presenter.getSpannableDateString());

            MainActivity activity = getMainActivity();
            Toolbar toolbar = activity.findViewById(R.id.toolbar);
            title = ViewUtils.getToolbarTextView(toolbar);

            presenter.setupDateSearch(title)
                    .requestData(new String[]{"", presenter.getDate()});
        }
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Job job = adapter.getItem(i);
        ProjectFragment fragment = ProjectFragment.newInstance(job);
        getMainActivity().getFragmentNavigator().pushFragment(fragment);
    }

    @Override
    public void onDataReceived(List<Job> data) {
        adapter.setData(data);
    }

    @Override
    public void onPause() {
        super.onPause();
        title.setOnClickListener(null); //remove listener after this fragment has paused
    }
}
