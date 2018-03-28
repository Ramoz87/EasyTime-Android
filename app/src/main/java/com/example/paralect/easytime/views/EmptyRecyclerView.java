package com.example.paralect.easytime.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.paralect.easytime.utils.DefAdapterDataObserver;

/**
 * Created by alexei on 26.12.2017.
 */

public class EmptyRecyclerView extends RecyclerView {
    private static final String TAG = EmptyRecyclerView.class.getSimpleName();

    private View mEmptyView;
    private final RecyclerView.AdapterDataObserver mObserver = new DefAdapterDataObserver() {
        @Override
        public void onDataChanged() {
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
            Log.d(TAG, String.format("list is %s", isEmpty ? "empty" : "not empty"));
            if (isEmpty) {
                mEmptyView.setVisibility(VISIBLE);
            } else {
                mEmptyView.setVisibility(GONE);
            }
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
