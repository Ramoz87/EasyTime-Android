package com.example.paralect.easytime.main.projects.project.jobexpenses.materials;

import android.content.res.Resources;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.utils.TextUtil;
import com.example.paralect.easytime.utils.ViewAnimationUtils;
import com.example.paralect.easytime.views.KeypadEditorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnTouch;

import static com.example.paralect.easytime.model.Constants.UNITY_PCS;

/**
 * Created by alexei on 17.01.2018.
 */

class MaterialExpensesAdapter extends RecyclerView.Adapter<MaterialExpensesAdapter.ViewHolder> {
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

    static class ViewHolder extends RecyclerView.ViewHolder implements KeypadEditorView.OnCompletionListener, TextWatcher {

        @BindView(R.id.materialName) TextView materialName;
        @BindView(R.id.materialNumber) TextView materialNumber;
        @BindView(R.id.checkBox) CheckBox checkBox;
        @BindView(R.id.material_expense_input_layout) TextInputLayout inputLayout;
        @BindView(R.id.material_expense_edit_text) TextInputEditText inputEditText;

        MaterialExpensesAdapter mAdapter;
        MaterialExpense mMaterialExpense;
        Resources mRes;

        @OnCheckedChanged(R.id.checkBox)
        void onCheckedChanged(CheckBox checkBox, boolean isChecked) {
            mMaterialExpense.isAdded = isChecked;
            OnCheckedCountChangeListener listener = mAdapter.onCheckedCountChangeListener;
            if (listener != null) {
                int totalCount = 0;
                for (MaterialExpense me : mAdapter.materialExpenses) {
                    if (me.isAdded) totalCount++;
                }
                listener.onCheckedCountChange(totalCount);
            }
        }

        @OnTouch({R.id.material_expense_edit_text, R.id.parent_layout})
        boolean onMaterialCountClick(View v, MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (v.getId() == inputEditText.getId()) {
                    Log.d(TAG, "on editor field count click");
                    KeypadEditorView editorView = mAdapter.keypadEditorView;
                    if (editorView != null) {
                        editorView.setupEditText(inputEditText);
                        inputEditText.requestFocus();
                        editorView.expand(true);
                    }
                    checkBox.setChecked(true);
                    afterTextChanged(inputEditText.getText());
                } else if (v.getId() == R.id.parent_layout) {
                    Log.d(TAG, "on parent layout click");
                    checkBox.setChecked(true);
                    afterTextChanged(inputEditText.getText());
                }
            }
            return true;
        }

        public ViewHolder(View itemView, MaterialExpensesAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mRes = itemView.getResources();
            this.mAdapter = adapter;

            inputEditText.setRawInputType(InputType.TYPE_NULL);
            inputEditText.setTextIsSelectable(true);

            KeypadEditorView editorView = mAdapter.keypadEditorView;
            if (editorView != null) {
                editorView.setupEditText(inputEditText);
                editorView.setOnCompletionListener(this);
            }
        }

        void bind(MaterialExpense materialExpense) {
            mMaterialExpense = materialExpense;
            Material material = mMaterialExpense.material;

            final int max = material.getStockQuantity();
            final String maxValue = String.valueOf(max);

            materialName.setText(material.getName());
            materialNumber.setText(mRes.getString(R.string.material_number, material.getMaterialNr()));

            inputLayout.setHint(UNITY_PCS);
            inputEditText.setText(maxValue);

            inputEditText.addTextChangedListener(this);

            inputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String text = inputEditText.getText().toString();
                        if (TextUtil.isEmpty(text)) {
                            inputEditText.setText(maxValue);
                        } else {
                            int value = Integer.parseInt(text);
                            if (value == 0)
                                inputEditText.setText(maxValue);
                        }
                    }
                }
            });
        }

        @Override
        public void onCompletion(KeypadEditorView keypadEditorView, String result) {
            Log.d(TAG, "on completion");
            inputEditText.clearFocus();
            keypadEditorView.collapse(true);
        }

        private int valueFromString(String result) {
            return result.isEmpty() ? 0 : Integer.valueOf(result);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            final int max = mMaterialExpense.material.getStockQuantity();
            final String maxValue = String.valueOf(max);
            if (TextUtil.isNotEmpty(text)) {
                int value = Integer.parseInt(text);
                boolean showError = value > max;
                if (showError) {
                    text = maxValue;
                    inputEditText.setText(maxValue);
                    inputEditText.selectAll();
                    ViewAnimationUtils.shakeAnimation(inputLayout);
                }
            } else {
                inputLayout.setErrorEnabled(false);
            }

            mMaterialExpense.count = valueFromString(text);
        }
    }
}
