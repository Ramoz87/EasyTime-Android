package com.example.paralect.easytime.main.customers.customer;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.ProjectType;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.utils.TextUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 27.12.2017.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    private List<Job> jobs;

    public JobAdapter() {

    }

    public void setData(List<Job> jobs) {
        this.jobs = jobs;
        notifyDataSetChanged();
    }

    @Override
    public JobAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobAdapter.ViewHolder holder, int position) {
        Job item = getItem(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return jobs != null ? jobs.size() : 0;
    }

    public Job getItem(int position) {
        return jobs.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.jobName) TextView jobName;
        @BindView(R.id.jobStatus) TextView jobStatus;
        @BindView(R.id.jobCustomer) TextView jobCustomer;
        @BindView(R.id.jobTerm) TextView jobTerm;
        Resources res;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            res = itemView.getResources();
        }

        void bind(Job job) {
            jobName.setText(res.getString(R.string.job_name_and_address, job.getNumber(), job.getName()));
            jobCustomer.setVisibility(View.GONE);
            jobTerm.setText(job.getStringDate());
            jobStatus.setText(job.getStatus().getName());
        }
    }
}
