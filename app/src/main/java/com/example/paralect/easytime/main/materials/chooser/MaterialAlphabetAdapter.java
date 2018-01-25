package com.example.paralect.easytime.main.materials.chooser;

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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 04.01.2018.
 */

public class MaterialAlphabetAdapter extends AlphabetStickyAdapter<Material> {

    private OnCheckedChangeListener onCheckedChangeListener;

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

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(Material material, boolean isChecked);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.setChecked(!checkBox.isChecked());
                }
            });
        }

        void bind(Material material) {
            this.material = material;
            checkBox.setOnCheckedChangeListener(null); // dirty
            checkBox.setChecked(material.isAdded());
            checkBox.setOnCheckedChangeListener(this);

            materialName.setText(material.getName());
            String number = materialNumber.getResources().getString(R.string.material_number, material.getSerialNr());
            materialNumber.setText(number);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            OnCheckedChangeListener listener = adapter.onCheckedChangeListener;
            if (listener != null) {
                listener.onCheckedChange(material, isChecked);
            }
        }
    }
}
