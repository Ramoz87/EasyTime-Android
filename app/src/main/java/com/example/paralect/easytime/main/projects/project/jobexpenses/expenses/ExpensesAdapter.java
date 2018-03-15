package com.example.paralect.easytime.main.projects.project.jobexpenses.expenses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.utils.CollectionUtil;
import com.paralect.expensesormlite.Expense;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 15.01.2018.
 */

public class ExpensesAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<Expense> mDefaultExpenses = Collections.emptyList();
    private List<Expense> mOtherExpenses = Collections.emptyList();

    public void setExpenses(List<Expense> defaultExpenses, List<Expense> otherExpenses) {
        if (CollectionUtil.isEmpty(defaultExpenses))
            mDefaultExpenses.clear();
        else
            mDefaultExpenses = defaultExpenses;

        if (CollectionUtil.isEmpty(otherExpenses))
            mOtherExpenses.clear();
        else
            mOtherExpenses = otherExpenses;

        notifyDataSetChanged();
    }

    public void addExpense(Expense expense) {
        if (mOtherExpenses == null) {
            mOtherExpenses = new ArrayList<>();
        }
        mOtherExpenses.add(expense);
        notifyDataSetChanged();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_header, parent, false);
        }

        ((TextView) convertView).setText(isBasic(position) ? R.string.basic : R.string.often_used);
        return convertView;
    }

    private boolean isBasic(int position) {
        return position < mDefaultExpenses.size();
    }

    @Override
    public long getHeaderId(int position) {
        return isBasic(position) ? 1 : 2;
    }

    @Override
    public int getCount() {
        return mDefaultExpenses.size() /*for mDefaultExpenses*/
                + mOtherExpenses.size() /*other expenses*/;
    }

    @Override
    public Expense getItem(int i) {
        if (i < mDefaultExpenses.size()) return mDefaultExpenses.get(i);
        else return mOtherExpenses.get(i - (mDefaultExpenses.size()));
    }

    @Override
    public long getItemId(int i) {
        Expense item = getItem(i);
        if (item != null) return item.getName().hashCode();
        else return 1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_expense, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            view.setTag(vh);
        }

        ViewHolder vh = (ViewHolder) view.getTag();
        Expense expense = getItem(i);
        vh.bind(expense);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.expenseName) TextView name;
        @BindView(R.id.divider) View divider;

        ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        void bind(Expense expense) {
            name.setText(expense.getName());
        }
    }
}
