package com.example.paralect.easytime.main.expenses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Expense;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 15.01.2018.
 */

public class ExpensesAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<Expense> defaultExpenses;
    private List<Expense> otherExpenses;

    public ExpensesAdapter(List<Expense> defaultExpenses) {
        this.defaultExpenses = defaultExpenses;
    }

    public void setOtherExpenses(List<Expense> otherExpenses) {
        this.otherExpenses = otherExpenses;
        notifyDataSetChanged();
    }

    public void setDefaultExpenses(List<Expense> defaultExpenses) {
        this.defaultExpenses = defaultExpenses;
        notifyDataSetChanged();
    }

    public void setExpenses(List<Expense> defaultExpenses, List<Expense> otherExpenses) {
        this.defaultExpenses = defaultExpenses;
        this.otherExpenses = otherExpenses;
        notifyDataSetChanged();
    }

    public void addExpense(Expense expense) {
        if (otherExpenses == null) {
            otherExpenses = new ArrayList<>();
        }
        otherExpenses.add(expense);
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
        return position <= defaultExpenses.size();
    }

    @Override
    public long getHeaderId(int position) {
        return isBasic(position) ? 1 : 2;
    }

    @Override
    public int getCount() {
        return (defaultExpenses != null ? defaultExpenses.size() : 0) /*for defaultExpenses*/
                + 1 /*expense creator*/
                + (otherExpenses == null ? 0 : otherExpenses.size()) /*other expenses*/;
    }

    @Override
    public Expense getItem(int i) {
        if (i < defaultExpenses.size()) return defaultExpenses.get(i);
        else if (i == defaultExpenses.size()) return null;
        else return otherExpenses.get(i - (defaultExpenses.size() + 1));
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
            if (expense == null) {
                // divider.setVisibility(View.GONE);
                name.setText(R.string.other_expenses);
            } else {
                name.setText(expense.getName());
            }
        }
    }
}
