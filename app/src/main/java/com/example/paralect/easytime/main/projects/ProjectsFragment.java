package com.example.paralect.easytime.main.projects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
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
import com.example.paralect.easytime.main.search.ISearchDataView;
import com.example.paralect.easytime.utils.ViewUtils;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.projects.project.ProjectFragment;
import com.example.paralect.easytime.model.Job;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 26.12.2017.
 */

public class ProjectsFragment extends AbsStickyFragment implements ISearchDataView<List<Job>> {
    private static final String TAG = ProjectsFragment.class.getSimpleName();

    private final ProjectsPresenter presenter = new ProjectsPresenter();
    private ProjectStickyAdapter adapter = new ProjectStickyAdapter();
    private FragmentNavigator navigator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (FragmentNavigator) context;

        // TODO set Title
//        TextView textView = getMainActivity().getTitleTextView();
//        textView.setText("14 December");
//        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0);
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
            // TODO set title with arrow
//            String text = "14 December";
//            String space = "   ";
//            SpannableString ss = new SpannableString(text + space);
//            Drawable d = getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
//            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//            ImageSpan span = ViewUtils.getImageSpan(d);
//            ss.setSpan(span, text.length(), text.length() + space.length(), 0);
//            actionBar.setTitle(ss);
//
//            presenter.getTitle("14 December");
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
}
