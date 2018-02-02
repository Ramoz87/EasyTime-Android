package com.example.paralect.easytime.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;

import com.example.paralect.easytime.utils.anim.AnimUtils;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by alexei on 26.12.2017.
 */

public class EmptyRecyclerView extends RecyclerView {

    private View mEmptyView;
    private final RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
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
            // mEmptyView.setVisibility(isEmpty ? VISIBLE : GONE);
            if (isEmpty) {
                AnimUtils.performAppearing(mEmptyView, 100, 0);
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
