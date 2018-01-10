package com.example.paralect.easytime.main.projects;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.paralect.easytime.main.FragmentNavigator;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.main.MainActivity;
import com.example.paralect.easytime.main.search.ISearchDataView;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.projects.project.ProjectFragment;
import com.example.paralect.easytime.model.Job;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 26.12.2017.
 */

public class ProjectsFragment extends AbsStickyFragment implements ISearchDataView<List<Job>>, DatePickerDialog.OnDateSetListener {
    private static final String TAG = ProjectsFragment.class.getSimpleName();

    private final ProjectsPresenter presenter = new ProjectsPresenter();
    private ProjectStickyAdapter adapter = new ProjectStickyAdapter();
    private FragmentNavigator navigator;
    private TextView title;

    // start value
    private int year = 2018;
    private int month = 12;
    private int day = 14;

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
        return adapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        presenter.setSearchDataView(this)
                .setupSearch(menu, R.id.item_search)
                .requestData("");
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            String dateString = CalendarUtils.getDateString(year, month, day);
            SpannableString ss = getSpannableDate(dateString);
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

            if (title != null) {
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = "clicked on action bar";
                        Log.d(TAG, message);
                        // Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        chooseDate();
                    }
                });
            }
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
        navigator.pushFragment(fragment);
    }

    @Override
    public void onDataReceived(List<Job> data) {
        adapter.setData(data);
    }

    private void chooseDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        year = i;
        month = i1;
        day = i2;
        String dateString = CalendarUtils.getDateString(i, i1, i2);
        SpannableString spannableDate = getSpannableDate(dateString);
        title.setText(spannableDate);
    }

    private SpannableString getSpannableDate(String dateString) {
        String space = "   ";
        SpannableString ss = new SpannableString(dateString + space);
        Drawable d = getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        //ImageSpan span = ViewUtils.getImageSpan(d);
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
        ss.setSpan(span, dateString.length(), dateString.length() + space.length(), 0);
        return ss;
    }
}
