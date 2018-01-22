package com.example.paralect.easytime.main.projects.project.jobexpenses.materials;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.views.KeypadEditorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by alexei on 17.01.2018.
 */

public class MaterialExpensesAdapter extends RecyclerView.Adapter<MaterialExpensesAdapter.ViewHolder> {
    private static final String TAG = MaterialExpensesAdapter.class.getSimpleName();

    private List<Material> materials;
    private KeypadEditorView keypadEditorView;
    private List<MaterialExpense> materialExpenses = new ArrayList<>();
    private OnCheckedCountChangeListener onCheckedCountChangeListener;

    public void setKeypadEditorView(KeypadEditorView editorView) {
        this.keypadEditorView = editorView;
    }

    public void setData(List<Material> materials) {
        this.materials = materials;
        materialExpenses.clear();
        for (Material material : materials) {
            materialExpenses.add(new MaterialExpense(material));
        }
        notifyDataSetChanged();
    }

    private Material getItem(int position) {
        return materials.get(position);
    }

    private MaterialExpense getMaterialExpense(int position) {
        return materialExpenses.get(position);
    }

    public List<Material> getItems() {
        return materials;
    }

    public List<MaterialExpense> getCheckedMaterials() {
        return materialExpenses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_expense, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MaterialExpense item = getMaterialExpense(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return materials != null ? materials.size() : 0;
    }

    public void setOnCheckedCountChangeListener(OnCheckedCountChangeListener onCheckedCountChangeListener) {
        this.onCheckedCountChangeListener = onCheckedCountChangeListener;
    }

    public interface OnCheckedCountChangeListener {
        void onCheckedCountChange(int totalCount);
    }

    static class ViewHolder extends RecyclerView.ViewHolder
            implements KeypadEditorView.OnCompletionListener {

        @BindView(R.id.materialName) TextView materialName;
        @BindView(R.id.materialNumber) TextView materialNumber;
        @BindView(R.id.switcher) ViewSwitcher switcher;
        @BindView(R.id.materialCount) TextView materialCount;
        @BindView(R.id.materialCountEditor) EditText materialCountEditor;
        @BindView(R.id.checkBox) CheckBox checkBox;

        @OnCheckedChanged(R.id.checkBox)
        void onCheckedChanged(CheckBox checkBox, boolean isChecked) {
            materialExpense.isAdded = isChecked;
            OnCheckedCountChangeListener listener = adapter.onCheckedCountChangeListener;
            if (listener != null) {
                int totalCount = 0;
                for (MaterialExpense me : adapter.materialExpenses) {
                    if (me.isAdded) totalCount++;
                }
                listener.onCheckedCountChange(totalCount);
            }
        }

        @OnFocusChange(R.id.materialCountEditor)
        void onFocusChange(View view, boolean b) {
            Log.d(TAG, "switcher changed focus to " + b);
            if (!b) switcher.showPrevious();
        }

        @OnClick(R.id.materialCount)
        void onMaterialCountClick(TextView materialCount) {
            Log.d(TAG, "on materialExpense count click");
            KeypadEditorView editorView = adapter.keypadEditorView;
            if (editorView != null) {
                editorView.setupEditText(materialCountEditor);
                editorView.setOnCompletionListener(this);
                switcher.showNext();
                materialCountEditor.requestFocus();
                editorView.expand(true);
            }
        }

        MaterialExpensesAdapter adapter;
        MaterialExpense materialExpense;
        Resources res;

        public ViewHolder(View itemView, MaterialExpensesAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            res = itemView.getResources();
            this.adapter = adapter;

            materialCountEditor.setRawInputType(InputType.TYPE_CLASS_TEXT);
            materialCountEditor.setTextIsSelectable(true);
            materialCountEditor.requestFocus();
        }

        void bind(MaterialExpense materialExpense) {
            this.materialExpense = materialExpense;
            Material material = materialExpense.material;
            materialName.setText(material.getName());
            materialNumber.setText(res.getString(R.string.material_number, material.getMaterialNr()));
            materialCount.setText(String.valueOf(material.getStockQuantity()));
        }

        @Override
        public void onCompletion(KeypadEditorView keypadEditorView, String result) {
            Log.d(TAG, "on completion");
            materialCount.clearFocus();
            keypadEditorView.collapse(true);
            materialExpense.count = valueFromString(result);
        }

        private int valueFromString(String result) {
            return result.isEmpty() ? 0 : Integer.valueOf(result);
        }
    }
}
