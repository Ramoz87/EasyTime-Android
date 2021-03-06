package com.example.paralect.easytime.main.projects.project.objectsofproject;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            view = inflater.inflate(R.layout.item_project, viewGroup, false);
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
        @BindView(R.id.jobStatus) TextView jobStatus;
        @BindView(R.id.jobCustomer) TextView jobCustomer;
        @BindView(R.id.jobTerm) TextView jobTerm;
        Resources res;

        ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            res = itemView.getResources();
        }

        void bind(Object job) {
            jobName.setText(res.getString(R.string.job_name_and_address, job.getNumber(), job.getName()));
            jobCustomer.setText(job.getCustomer().getCompanyName());
            jobTerm.setText(job.getStringDate());
            jobStatus.setText(job.getStatus().getName());
        }
    }
}
