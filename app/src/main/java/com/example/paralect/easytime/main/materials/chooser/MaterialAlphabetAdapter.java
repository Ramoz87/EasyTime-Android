package com.example.paralect.easytime.main.materials.chooser;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.AlphabetStickyAdapter;
import com.example.paralect.easytime.model.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by alexei on 04.01.2018.
 */

public class MaterialAlphabetAdapter extends AlphabetStickyAdapter<Material> {

    private List<Material> checkedMaterials = new ArrayList<>();
    private OnCheckedCountChangeListener onCheckedCountChangeListener;

    public List<Material> getCheckedMaterials() {
        return checkedMaterials;
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getName().hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_material_to_add, viewGroup, false);
            ViewHolder vh = new ViewHolder(view, this);
            view.setTag(vh);
        }

        ViewHolder vh = (ViewHolder) view.getTag();
        Material item = getItem(i);
        vh.bind(item);
        return view;
    }

    public void setOnCheckedCountChangeListener(OnCheckedCountChangeListener onCheckedCountChangeListener) {
        this.onCheckedCountChangeListener = onCheckedCountChangeListener;
    }

    public interface OnCheckedCountChangeListener {
        void onCheckedCountChange(int totalCount);
    }

    class ViewHolder implements CompoundButton.OnCheckedChangeListener {
        @BindView(R.id.materialName)
        TextView materialName;

        @BindView(R.id.materialNumber)
        TextView materialNumber;

        @BindView(R.id.checkBox)
        CheckBox checkBox;

        private MaterialAlphabetAdapter adapter;
        private Material material;

        ViewHolder(View itemView, MaterialAlphabetAdapter adapter) {
            ButterKnife.bind(this, itemView);
            this.adapter = adapter;
        }

        void bind(Material material) {
            this.material = material;
            List<Material> checkedMaterials = adapter.getCheckedMaterials();
            boolean isChecked = checkedMaterials.contains(material);
            checkBox.setOnCheckedChangeListener(null); // dirty
            checkBox.setChecked(isChecked);
            checkBox.setOnCheckedChangeListener(this);

            materialName.setText(material.getName());
            String number = materialNumber.getResources().getString(R.string.material_number, material.getSerialNr());
            materialNumber.setText(number);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            List<Material> checkedMaterials = adapter.checkedMaterials;
            if (isChecked) {
                checkedMaterials.add(material);
            } else {
                checkedMaterials.remove(material);
            }
            OnCheckedCountChangeListener listener = adapter.onCheckedCountChangeListener;
            if (listener != null) {
                listener.onCheckedCountChange(checkedMaterials.size());
            }
        }
    }
}
