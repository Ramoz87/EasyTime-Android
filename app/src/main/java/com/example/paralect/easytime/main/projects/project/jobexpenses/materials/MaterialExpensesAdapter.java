package com.example.paralect.easytime.main.projects.project.jobexpenses.materials;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Material;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 17.01.2018.
 */

public class MaterialExpensesAdapter extends RecyclerView.Adapter<MaterialExpensesAdapter.ViewHolder> {

    private List<Material> materials;

    public void setData(List<Material> materials) {
        this.materials = materials;
        notifyDataSetChanged();
    }

    private Material getItem(int position) {
        return materials.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Material item = getItem(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return materials != null ? materials.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.materialName) TextView materialName;
        @BindView(R.id.materialNumber) TextView materialNumber;

        Resources res;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            res = itemView.getResources();
        }

        void bind(Material material) {
            materialName.setText(material.getName());
            materialNumber.setText(res.getString(R.string.material_number, material.getMaterialNr()));
        }
    }
}
