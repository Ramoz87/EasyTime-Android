package com.example.paralect.easytime.main.projects.project.objectsofproject;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.AlphabetStickyAdapter;
import com.example.paralect.easytime.model.Object;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 17.01.2018.
 */

public class ObjectsOfProjectAdapter extends AlphabetStickyAdapter<Object> {

    @Override
    public long getItemId(int i) {
        return getItem(i).getName().hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_object, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            view.setTag(vh);
        }

        ViewHolder vh = (ViewHolder) view.getTag();
        Object o = getItem(i);
        vh.bind(o);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.jobName) TextView jobName;
        @BindView(R.id.jobNumber) TextView jobNumber;
        Resources res;

        ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            res = itemView.getResources();
        }

        void bind(Object object) {
            jobName.setText(object.getName());
            jobNumber.setText(res.getString(R.string.job_number, object.getNumber()));
        }
    }
}
