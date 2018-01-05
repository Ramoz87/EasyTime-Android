package com.example.paralect.easytime.main;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.paralect.easytime.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by alexei on 26.12.2017.
 */

public abstract class AbsStickyFragment extends BaseFragment
        implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private static final String TAG = AbsStickyFragment.class.getSimpleName();

    @BindView(R.id.sticky_list_headers_list_view)
    StickyListHeadersListView stickyListHeadersListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_abs_sticky, parent, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        Log.d(TAG, "initializing sticky list view");
        StickyListHeadersAdapter adapter = buildAdapter();
        stickyListHeadersListView.setAdapter(adapter);
        stickyListHeadersListView.setOnItemClickListener(this);
        stickyListHeadersListView.setOnItemLongClickListener(this);
    }

    public abstract StickyListHeadersAdapter buildAdapter();

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }
}
