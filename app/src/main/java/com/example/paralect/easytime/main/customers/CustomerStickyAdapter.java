package com.example.paralect.easytime.main.customers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.AlphabetStickyAdapter;
import com.example.paralect.easytime.model.Customer;

import java.util.List;
import java.util.SortedMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 27.12.2017.
 */

public class CustomerStickyAdapter extends AlphabetStickyAdapter<Customer> implements StickyListHeadersAdapter {

    @Override
    public long getItemId(int i) {
        return getItem(i).getCompanyName().hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Customer item = getItem(i);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_customer, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            view.setTag(vh);
        }

        ViewHolder vh = (ViewHolder) view.getTag();
        vh.bind(item);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.companyName)
        TextView companyName;

        @BindView(R.id.jobs)
        TextView jobs;

        ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }

        void bind(Customer customer) {
            companyName.setText(customer.getCompanyName());
        }
    }
}
