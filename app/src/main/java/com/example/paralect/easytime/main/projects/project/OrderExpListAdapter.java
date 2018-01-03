package com.example.paralect.easytime.main.projects.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paralect.easytime.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 28.12.2017.
 */

public class OrderExpListAdapter extends BaseExpandableListAdapter {
    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int i) {
        if (i == 0) return 3;
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.include_info_header, viewGroup, false);
            HeaderViewHolder vh = new HeaderViewHolder(convertView);
            convertView.setTag(vh);
        }

        HeaderViewHolder vh = (HeaderViewHolder) convertView.getTag();

        if (i == 0) {
            vh.title.setText(R.string.instructions);
        }

        if (isExpanded){
            // TO DO
        }
        else{
            // TO DO
        }


        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.include_info_item, viewGroup, false);
            ItemViewHolder vh = new ItemViewHolder(convertView);
            convertView.setTag(vh);
        }

        ItemViewHolder vh = (ItemViewHolder) convertView.getTag();
        if (i1 == 0) {
            vh.icon.setImageResource(R.drawable.ic_phone);
            vh.text.setText(R.string.placeholder_project_info_contact);
        } else if (i1 == 1) {
            vh.icon.setImageResource(R.drawable.ic_checkpoint);
            vh.text.setText(R.string.placeholder_project_info_address);
        } else if (i1 == 2) {
            vh.icon.setImageResource(R.drawable.ic_watch);
            vh.text.setText(R.string.placeholder_project_info_delivery_time);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    static class ItemViewHolder {
        @BindView(R.id.icon)
        ImageView icon;

        @BindView(R.id.text)
        TextView text;

        ItemViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    static class HeaderViewHolder {
        @BindView(R.id.title)
        TextView title;

        HeaderViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
