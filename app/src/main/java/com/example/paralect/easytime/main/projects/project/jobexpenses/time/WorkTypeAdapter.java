package com.example.paralect.easytime.main.projects.project.jobexpenses.time;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.views.adapter.RecyclerViewAdapter;
import com.example.paralect.easytime.views.adapter.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 26/01/2018.
 */

class WorkTypeAdapter extends RecyclerViewAdapter<WorkTypeAdapter.ViewHolder> {

    private List<Type> mTypes = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_type, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        addOnClickActions(holder);
        Type type = getItem(position);
        holder.setType(type);
    }

    @Override
    public int getItemCount() {
        return mTypes.size();
    }

    public Type getItem(int position) {
        return mTypes.get(position);
    }

    public void setTypes(List<Type> types) {
        mTypes = types;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerViewHolder {

        private final TextView titleView;

        public ViewHolder(final View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.name);
        }

        public void setType(Type type) {
            titleView.setText(type.getName());
        }
    }

}