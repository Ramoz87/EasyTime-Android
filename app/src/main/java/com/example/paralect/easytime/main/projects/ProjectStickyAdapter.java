package com.example.paralect.easytime.main.projects;

import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 26.12.2017.
 */

public class ProjectStickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private static final int TYPE_PROJECT = 0;
    private static final int TYPE_ORDER = 1;
    private static final int TYPE_OBJECT = 2;

    private List<Project> projects = new ArrayList<>();
    private List<Object> objects = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public ProjectStickyAdapter(List<Job> jobs) {
        init(jobs);
    }

    private void init(List<Job> jobs) {
        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);
            boolean found = false;
            if (job instanceof Project) {
                projects.add((Project) job);
                found = true;
            } else if (job instanceof Object) {
                objects.add((Object) job);
                found = true;
            } else if (job instanceof Order) {
                orders.add((Order) job);
                found = true;
            }

            if (found) {
                jobs.remove(i);
                i--;
            }
        }
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_header, parent, false);
        }
        @StringRes int resId;
        if (type == TYPE_PROJECT) {
            resId = R.string.header_projects;
        } else if (type == TYPE_OBJECT) {
            resId = R.string.header_objects;
        } else {
            resId = R.string.header_orders;
        }
        ((TextView) convertView).setText(resId);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return getItemViewType(position);
    }

    @Override
    public int getCount() {
        return projects.size() + objects.size() + orders.size();
    }

    @Override
    public Job getItem(int i) {
        int type = getItemViewType(i);
        if (type == TYPE_PROJECT)
            return projects.get(i);
        else if (type == TYPE_OBJECT)
            return objects.get(i - projects.size());
        else return orders.get(i - projects.size() - objects.size());
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getJobId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);
        if (type == TYPE_PROJECT) {
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return inflater.inflate(R.layout.item_project, viewGroup, false);
            }
        } else if (type == TYPE_OBJECT) {
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return inflater.inflate(R.layout.item_object, viewGroup, false);
            }
        } else if (type == TYPE_ORDER) {
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return inflater.inflate(R.layout.item_order, viewGroup, false);
            }
        } else view = new View(viewGroup.getContext());

        return view;
    }

    @Override
    public int getItemViewType(int i) {
        if (i < projects.size())
            return TYPE_PROJECT;
        else if (i < projects.size() + objects.size())
            return TYPE_OBJECT;
        else return TYPE_ORDER;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }
}
