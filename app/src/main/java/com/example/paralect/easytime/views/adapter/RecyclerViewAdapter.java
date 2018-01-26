package com.example.paralect.easytime.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Oleg Tarashkevich on 14.05.16.
 */
public abstract class RecyclerViewAdapter<T extends RecyclerViewHolder> extends RecyclerView.Adapter<T> {

    protected OnItemClickListener onItemClickListener;

    protected void addOnClickActions(RecyclerViewHolder viewHolder){
        viewHolder.setOnItemViewHolderClickListener(new RecyclerViewHolder.OnItemViewHolderClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(view, position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                if (onItemClickListener != null)
                    onItemClickListener.onLongItemClick(view, position);
            }
        });
    }

    /**
     * Interface
     */

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onLongItemClick(View view, int position);
    }

    public static class SimpleOnItemClickListener implements OnItemClickListener{

        @Override
        public void onItemClick(View view, int position) {

        }

        @Override
        public void onLongItemClick(View view, int position) {

        }
    }
}