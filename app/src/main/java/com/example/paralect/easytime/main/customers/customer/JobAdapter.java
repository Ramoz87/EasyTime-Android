package com.example.paralect.easytime.main.customers.customer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Job;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 27.12.2017.
 */

public class JobAdapter<E extends Job> extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    private List<E> jobs;

    public JobAdapter(List<E> jobs) {
        this.jobs = jobs;
    }

    @Override
    public JobAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobAdapter.ViewHolder holder, int position) {
        E item = getItem(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public E getItem(int position) {
        return jobs.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.jobName) TextView jobName;
        @BindView(R.id.jobStatus) TextView jobStatus;
        @BindView(R.id.jobNumber) TextView jobNumber;
        @BindView(R.id.jobTerm) TextView jobTerm;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Job job) {
            jobName.setText(job.getName());
            jobNumber.setText(itemView.getContext().getString(R.string.job_number, job.getNumber()));
        }
    }
}
