package com.example.paralect.easytime.main.projects;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.ProjectType;
import com.example.paralect.easytime.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import static com.example.paralect.easytime.model.ProjectType.Type.TYPE_OBJECT;
import static com.example.paralect.easytime.model.ProjectType.Type.TYPE_PROJECT;

/**
 * Created by alexei on 26.12.2017.
 */

public class ProjectStickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<Job> mJobs = new ArrayList<>();

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
        return mJobs.size();
    }

    @Override
    public Job getItem(int i) {
        return mJobs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getJobId().hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);
        if (view == null) { // creating if view is null
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_project, viewGroup, false);
            JobViewHolder vh = new JobViewHolder(view);
            view.setTag(vh);
        }

        // binding
        JobViewHolder vh = (JobViewHolder) view.getTag();
        vh.bind(getItem(i));

        return view;
    }

    @Override
    @ProjectType.Type
    public int getItemViewType(int i) {
        return getItem(i).getProjectType();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    public void setData(List<Job> jobs) {
        if (CollectionUtil.isEmpty(jobs))
            mJobs.clear();
        else
            mJobs = jobs;
        notifyDataSetChanged();
    }

    // region CellViewHolderBase
    static class JobViewHolder {
        @BindView(R.id.jobName) TextView jobName;
        @BindView(R.id.jobStatus) TextView jobStatus;
        @BindView(R.id.jobCustomer) TextView jobCustomer;
        @BindView(R.id.jobTerm) TextView jobTerm;
        Resources res;

        JobViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            res = itemView.getResources();
        }

        void bind(Job job) {
            jobName.setText(res.getString(R.string.job_name_and_address, job.getNumber(), job.getName()));
            // jobName.setText(project.getStatusId());
            jobCustomer.setText(job.getCustomer().getCompanyName());
            jobTerm.setText(job.getStringDate());
            jobStatus.setText(job.getStatus().getName());
        }
    }
    // endregion

}
