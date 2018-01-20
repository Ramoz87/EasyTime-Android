package com.example.paralect.easytime.main.projects.project.details;

import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.Material;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 16.01.2018.
 */

public class ProjectExpensesAdapter extends RecyclerView.Adapter<ProjectExpensesAdapter.ExpenseViewHolder> {

    private static final int TYPE_MATERIAL = 0;
    private static final int TYPE_EXPENSE = 1;
    private static final int TYPE_TOTAL_COUNT = 2;

    private List<Expense> expenses;
    private Expense total;

    public ProjectExpensesAdapter() {

    }

    public void setData(List<Expense> expenses) {
        this.expenses = expenses;
        initTotal();
        notifyDataSetChanged();
    }

    private void initTotal() {
        total = new Expense() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public boolean isMaterialExpense() {
                return false;
            }

            @Override
            public int getValue() {
                int totalPrice = 0;
                for (Expense e : expenses) {
                    totalPrice += e.getValue();
                }
                return totalPrice;
            }
        };
    }

    private Expense getItem(int position) {
        if (position == expenses.size()) return total;
        else return expenses.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = getItemCount();
        if (position == (itemCount - 1)) {
            return TYPE_TOTAL_COUNT;
        } else {
            Expense e = getItem(position);
            return e.isMaterialExpense() ? TYPE_MATERIAL : TYPE_EXPENSE;
        }
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if (viewType == TYPE_TOTAL_COUNT) {
            view = inflater.inflate(R.layout.item_project_total_expense, parent, false);
            return new TotalExpenseViewHolder(view);
        } else {
            @LayoutRes int id = R.layout.item_project_expense;
            view = inflater.inflate(id, parent, false);
            return new ExpenseViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        Expense item = getItem(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return expenses != null ? expenses.size() + 1 /*for total count*/ : 0;
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.expenseName)
        TextView expenseName;

        @BindView(R.id.pricePerUnit)
        TextView pricePerUnit;

        @BindView(R.id.expenseTotalPrice)
        TextView totalPrice;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Expense expense) {
            Resources res = itemView.getResources();
            expenseName.setText(expense.getName());
            int price = expense.getValue();
            if (expense.isMaterialExpense()) {
                Material material = expense.getMaterial();
                pricePerUnit.setVisibility(View.VISIBLE);
                pricePerUnit.setText(res.getString(R.string.expense_price, material.getPricePerUnit()));
            } else {
                pricePerUnit.setVisibility(View.GONE);
            }

            totalPrice.setText(res.getString(R.string.expense_price, price));
        }
    }

    static class TotalExpenseViewHolder extends ExpenseViewHolder {

        public TotalExpenseViewHolder(View itemView) {
            super(itemView);
        }

        void bind(Expense expense) {
            Resources res = itemView.getResources();
            pricePerUnit.setVisibility(View.GONE);
            expenseName.setText(R.string.total_expense);
            int price = expense.getValue();
            totalPrice.setText(res.getString(R.string.expense_price, price));
        }
    }
}
