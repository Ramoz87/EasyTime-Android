package com.example.paralect.easytime.utils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by alexei on 06.02.2018.
 */

public abstract class DefAdapterDataObserver extends RecyclerView.AdapterDataObserver {

    @Override
    public void onChanged() {
        super.onChanged();
        onDataChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        onDataChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        super.onItemRangeChanged(positionStart, itemCount, payload);
        onDataChanged();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        onDataChanged();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        onDataChanged();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        onDataChanged();
    }

    public abstract void onDataChanged();
}
