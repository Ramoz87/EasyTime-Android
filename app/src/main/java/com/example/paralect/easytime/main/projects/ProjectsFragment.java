package com.example.paralect.easytime.main.projects;

import android.content.Context;
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

import java.util.Calendar;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 26.12.2017.
 */

public class ProjectsFragment extends AbsStickyFragment implements IDataView<List<Job>> {
    private static final String TAG = ProjectsFragment.class.getSimpleName();

    private Calendar calendar = Calendar.getInstance();
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
    public StickyListHeadersAdapter buildAdapter() {
        Log.d(TAG, "building sticky adapter");
        return adapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        String date = CalendarUtils.stringFromDate(calendar.getTime(), CalendarUtils.DEFAULT_DATE_FORMAT);
        presenter.setDataView(this)
                .setupQuerySearch((SearchView) menu.findItem(R.id.item_search).getActionView())
                .requestData(new String[]{"", date});
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            // String dateString = CalendarUtils.getDateString(year, month, day);
            SpannableString ss = CalendarUtils.getSpannableDateString(getContext(), calendar);
            actionBar.setTitle(ss);

            //presenter.getTitle("14 December");
            MainActivity activity = getMainActivity();
            Toolbar toolbar = activity.findViewById(R.id.toolbar);
            for (int i = 0; i < toolbar.getChildCount(); ++i) {
                View child = toolbar.getChildAt(i);
                if (child instanceof TextView) {
                    title = (TextView) child;
                    break;
                }
            }

            String date = CalendarUtils.stringFromDate(calendar.getTime(), CalendarUtils.DEFAULT_DATE_FORMAT);
            presenter.setDataView(this)
                    .setupDateSearch(title)
                    .requestData(new String[] {"", date});
        }
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
        getMainActivity().getFragmentNavigator().pushFragment(fragment);
    }

    @Override
    public void onDataReceived(List<Job> data) {
        adapter.setData(data);
    }
}
