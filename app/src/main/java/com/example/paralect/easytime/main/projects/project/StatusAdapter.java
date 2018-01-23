package com.example.paralect.easytime.main.projects.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Type;

import java.util.List;

/**
 * Created by alexei on 23.01.2018.
 */

public class StatusAdapter extends BaseAdapter implements SpinnerAdapter {
    private List<Type> statuses;

    public void setData(List<Type> statuses) {
        this.statuses = statuses;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return statuses != null ? statuses.size() : 0;
    }

    @Override
    public Type getItem(int i) {
        return statuses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getTypeId().hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_status, viewGroup, false);
        }

        Type status = getItem(i);
        ((TextView) view).setText(status.getName());
        return view;
    }
}
