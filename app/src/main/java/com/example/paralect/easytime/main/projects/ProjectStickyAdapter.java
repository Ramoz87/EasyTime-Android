package com.example.paralect.easytime.main.projects;

import android.support.annotation.Nullable;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import static com.example.paralect.easytime.main.projects.ProjectType.Type.TYPE_OBJECT;
import static com.example.paralect.easytime.main.projects.ProjectType.Type.TYPE_ORDER;
import static com.example.paralect.easytime.main.projects.ProjectType.Type.TYPE_PROJECT;

/**
 * Created by alexei on 26.12.2017.
 */

public class ProjectStickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private final List<Job> mJobs = new ArrayList<>();

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
            if (type == TYPE_PROJECT) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                view = inflater.inflate(R.layout.item_project, viewGroup, false);
                JobViewHolder vh = new JobViewHolder(view);
                view.setTag(vh);
            } else if (type == TYPE_ORDER) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                view = inflater.inflate(R.layout.item_order, viewGroup, false);
                OrderViewHolder vh = new OrderViewHolder(view);
                view.setTag(vh);
            } else if (type == TYPE_OBJECT) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                view = inflater.inflate(R.layout.item_object, viewGroup, false);
                ObjectViewHolder vh = new ObjectViewHolder(view);
                view.setTag(vh);
            } else view = new View(viewGroup.getContext());
        }

        // binding
        if (type == TYPE_PROJECT) {
            JobViewHolder vh = (JobViewHolder) view.getTag();
            vh.bind(getItem(i));
        } else if (type == TYPE_ORDER) {
            OrderViewHolder vh = (OrderViewHolder) view.getTag();
            vh.bind(getItem(i));
        } else if (type == TYPE_OBJECT) {
            ObjectViewHolder vh = (ObjectViewHolder) view.getTag();
            vh.bind(getItem(i));
        }

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
        mJobs.clear();
        mJobs.addAll(jobs);
        notifyDataSetChanged();
    }

    // region ViewHolder
    static class JobViewHolder {
        @BindView(R.id.jobName) TextView jobName;
        @BindView(R.id.jobStatus) TextView jobStatus;
        @BindView(R.id.jobCustomer) TextView jobCustomer;
        @BindView(R.id.jobNumber) TextView jobNumber;
        @Nullable
        @BindView(R.id.job_address_and_date) TextView addressAndDate;

        JobViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        void bind(Job job) {
            jobName.setText(job.getName());
            // jobName.setText(project.getStatusId());
            jobCustomer.setText(job.getCustomer().getCompanyName());
            String number = jobNumber.getResources().getString(R.string.job_number, job.getNumber());
            jobNumber.setText(number);
        }
    }

    static class OrderViewHolder extends JobViewHolder {

        OrderViewHolder(View itemView) {
            super(itemView);
        }

        void bind(Order order) {
            super.bind(order);
            // bind address and date
            if (addressAndDate != null) {
                String addressAndDateString = order.getAddress().getStreet() + ", " + order.getDeliveryTime();
                addressAndDate.setText(addressAndDateString);
            }
        }
    }

    static class ObjectViewHolder extends OrderViewHolder {

        ObjectViewHolder(View itemView) {
            super(itemView);
        }

        void bind(Object object) {
            super.bind(object);
            // address and date
            if (addressAndDate != null) {
                String addressAndDateString = object.getAddress()
                        .getStreet()
                        + ", "
                        + object.getDateStart();
                addressAndDate.setText(addressAndDateString);
            }
        }
    }
    // endregion

}
