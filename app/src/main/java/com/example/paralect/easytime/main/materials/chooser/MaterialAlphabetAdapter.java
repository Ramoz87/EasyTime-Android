package com.example.paralect.easytime.main.materials.chooser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.AlphabetStickyAdapter;
import com.example.paralect.easytime.model.Material;

import java.util.List;
import java.util.SortedMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 04.01.2018.
 */

public class MaterialAlphabetAdapter extends AlphabetStickyAdapter<Material> {

    public MaterialAlphabetAdapter(SortedMap<Character, List<Material>> sortedCustomers) {
        super(sortedCustomers);
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
            ViewHolder vh = new ViewHolder(view);
            view.setTag(vh);
        }

        ViewHolder vh = (ViewHolder) view.getTag();
        Material item = getItem(i);
        vh.bind(item);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.materialName)
        TextView materialName;

        @BindView(R.id.materialNumber)
        TextView materialNumber;

        ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        void bind(Material material) {
            materialName.setText(material.getName());
            String number = materialNumber.getResources().getString(R.string.material_number, material.getSerialNr());
            materialNumber.setText(number);
        }
    }
}
