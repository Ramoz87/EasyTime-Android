package com.example.paralect.easytime.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Oleg Tarashkevich on 14.05.16.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public RecyclerViewHolder(final View convertView) {
        super(convertView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemViewHolderClickListener != null)
                    onItemViewHolderClickListener.onItemClick(convertView, getAdapterPosition());
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemViewHolderClickListener != null)
                    onItemViewHolderClickListener.onLongItemClick(convertView, getAdapterPosition());
                return true;
            }
        });
    }

    /**
     * Interface
     */

    private OnItemViewHolderClickListener onItemViewHolderClickListener;

    public void setOnItemViewHolderClickListener(OnItemViewHolderClickListener onItemClickListener) {
        this.onItemViewHolderClickListener = onItemClickListener;
    }

    public interface OnItemViewHolderClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }
}