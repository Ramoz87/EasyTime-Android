package com.example.paralect.easytime.main.projects.project.invoice;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.InvoiceCell;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 31.01.2018.
 */

class ProjectInvoiceAdapter extends RecyclerView.Adapter<ProjectInvoiceAdapter.BaseCellViewHolder> {

    private List<InvoiceCell> mInvoices = Collections.emptyList();

    public void setData(List<InvoiceCell> cells) {
        mInvoices = cells;
        notifyDataSetChanged();
    }

    private InvoiceCell getItem(int position) {
        return mInvoices.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        InvoiceCell cell = getItem(position);
        return cell.invoiceCellType();
    }

    @Override
    public BaseCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BaseCellViewHolder holder = null;
        switch (viewType) {

            case InvoiceCell.Type.HEADER:
                View view = inflater.inflate(R.layout.item_invoice_header, parent, false);
                holder = new HeaderViewHolderBase(view);
                break;

            case InvoiceCell.Type.CELL:
                view = inflater.inflate(R.layout.item_invoice_cell, parent, false);
                holder = new CellViewHolderBase(view);
                break;

            case InvoiceCell.Type.TOTAL:
                view = inflater.inflate(R.layout.item_invoice_total, parent, false);
                holder = new TotalViewHolderBase(view);
                break;

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseCellViewHolder holder, int position) {
        InvoiceCell cell = getItem(position);
        holder.bind(cell);
    }

    @Override
    public int getItemCount() {
        return mInvoices.size();
    }

    static class HeaderViewHolderBase extends BaseCellViewHolder {

        HeaderViewHolderBase(View itemView) {
            super(itemView);
        }

        void bind(InvoiceCell cell) {
            nameTextView.setText(cell.name());
        }
    }

    static class CellViewHolderBase extends BaseCellViewHolder {

        CellViewHolderBase(View itemView) {
            super(itemView);
        }

        void bind(InvoiceCell cell) {
            nameTextView.setText(cell.name());
            priceTextView.setText(cell.value());
        }
    }

    static class TotalViewHolderBase extends CellViewHolderBase {

        TotalViewHolderBase(View itemView) {
            super(itemView);
        }

        void bind(InvoiceCell cell) {
            priceTextView.setText(cell.value());
        }
    }

    static abstract class BaseCellViewHolder extends RecyclerView.ViewHolder {

        @Nullable @BindView(R.id.invoice_name_text_view) TextView nameTextView;
        @Nullable @BindView(R.id.invoice_price_text_view) TextView priceTextView;

        BaseCellViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(InvoiceCell cell) {

        }
    }
}
