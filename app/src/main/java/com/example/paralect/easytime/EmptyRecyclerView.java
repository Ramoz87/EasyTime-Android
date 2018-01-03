package com.example.paralect.easytime;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by alexei on 26.12.2017.
 */

public class EmptyRecyclerView extends RecyclerView {

    private View mEmptyView;
    private final RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }
    };

    public EmptyRecyclerView(Context context) {
        this(context, null);
    }

    public EmptyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (mEmptyView != null) {
            boolean isEmpty = getAdapter() == null || getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(isEmpty ? VISIBLE : GONE);
        }
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mObserver); // remove observer from the old adapter
        }

        super.setAdapter(adapter);
        checkIfEmpty();

        if (adapter != null) {
            adapter.registerAdapterDataObserver(mObserver); // add observer to the new adapter
        }
    }

    public void setEmptyView(View view) {
        this.mEmptyView = view;
        checkIfEmpty();
    }

}
