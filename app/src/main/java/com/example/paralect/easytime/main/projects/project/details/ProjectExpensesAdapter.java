package com.example.paralect.easytime.main.projects.project.details;

import android.content.res.Resources;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Consumable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 16.01.2018.
 */

public class ProjectExpensesAdapter extends RecyclerView.Adapter<ProjectExpensesAdapter.ConsumableViewHolder> {

    private static final int TYPE_MATERIAL = 0;
    private static final int TYPE_EXPENSE = 1;
    private static final int TYPE_TOTAL_COUNT = 2;

    private List<Consumable> consumables;
    private Consumable total;

    public ProjectExpensesAdapter() {

    }

    public void setData(List<Consumable> consumables) {
        this.consumables = consumables;
        initTotal();
        notifyDataSetChanged();
    }

    private void initTotal() {
        total = new Consumable() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public boolean isMaterial() {
                return false;
            }

            @Override
            public int getStockQuantity() {
                return 1;
            }

            @Override
            public int getPricePerUnit() {
                int totalPrice = 0;
                for (Consumable c : consumables) {
                    totalPrice += c.getPricePerUnit() * c.getStockQuantity();
                }
                return totalPrice;
            }
        };
    }

    private Consumable getItem(int position) {
        if (position == consumables.size()) return total;
        else return consumables.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = getItemCount();
        if (position == (itemCount - 1)) {
            return TYPE_TOTAL_COUNT;
        } else {
            Consumable c = getItem(position);
            return c.isMaterial() ? TYPE_MATERIAL : TYPE_EXPENSE;
        }
    }

    @Override
    public ConsumableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if (viewType == TYPE_TOTAL_COUNT) {
            view = inflater.inflate(R.layout.item_project_total_expense, parent, false);
            return new TotalConsumableViewHolder(view);
        } else {
            @LayoutRes int id = R.layout.item_project_expense;
            view = inflater.inflate(id, parent, false);
            return new ConsumableViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ConsumableViewHolder holder, int position) {
        Consumable item = getItem(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return consumables != null ? consumables.size() + 1 /*for total count*/ : 0;
    }

    static class ConsumableViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.expenseName)
        TextView expenseName;

        @BindView(R.id.pricePerUnit)
        TextView pricePerUnit;

        @BindView(R.id.expenseTotalPrice)
        TextView totalPrice;

        public ConsumableViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Consumable consumable) {
            Resources res = itemView.getResources();
            expenseName.setText(consumable.getName());
            int price = consumable.getPricePerUnit() * consumable.getStockQuantity();
            totalPrice.setText(res.getString(R.string.expense_price, price));

            if (consumable.isMaterial()) {
                pricePerUnit.setVisibility(View.VISIBLE);
                pricePerUnit.setText(res.getString(R.string.expense_price, consumable.getPricePerUnit()));
            } else {
                pricePerUnit.setVisibility(View.GONE);
            }
        }
    }

    static class TotalConsumableViewHolder extends ConsumableViewHolder {

        public TotalConsumableViewHolder(View itemView) {
            super(itemView);
        }

        void bind(Consumable consumable) {
            Resources res = itemView.getResources();
            pricePerUnit.setVisibility(View.GONE);
            expenseName.setText(R.string.total_expense);
            int price = consumable.getPricePerUnit() * consumable.getStockQuantity();
            totalPrice.setText(res.getString(R.string.expense_price, price));
        }
    }
}
