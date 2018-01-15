package com.example.paralect.easytime.main.materials.chooser;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.views.KeypadEditorView;
import com.example.paralect.easytime.views.KeypadView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 12.01.2018.
 */

public class MaterialEditorFragment extends BaseFragment implements KeypadEditorView.OnCompletionListener {
    private static final String TAG = MaterialEditorFragment.class.getSimpleName();

    public static final String ARG_MATERIAL = "arg_material";

    @BindView(R.id.keypad) KeypadEditorView keypadEditorView;
    @BindView(R.id.materialName) TextView materialName;
    @BindView(R.id.materialNumber) TextView materialNumber;
    @BindView(R.id.materialCount) EditText materialCount;

    public static MaterialEditorFragment newInstance() {
        return new MaterialEditorFragment();
    }

    public static MaterialEditorFragment newInstance(Material material) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_MATERIAL, material);
        MaterialEditorFragment fragment = new MaterialEditorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_material_editor, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Material material = getMaterialArg();
        int count = material.getCount();
        if (count > 0) {
            materialCount.setText(String.valueOf(count));
        }

        materialCount.setRawInputType(InputType.TYPE_CLASS_TEXT);
        materialCount.setTextIsSelectable(true);
        materialCount.requestFocus();

        keypadEditorView.setOnCompletionListener(this);
        keypadEditorView.setupEditText(materialCount);

        materialName.setText(material.getName());
        materialNumber.setText(getResources().getString(R.string.material_number, material.getMaterialNr()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {

    }

    @Override
    public boolean needsOptionsMenu() {
        return false;
    }

    private Material getMaterialArg() {
        Bundle args = getArguments();
        if (args == null || !args.containsKey(ARG_MATERIAL)) return null;
        else return args.getParcelable(ARG_MATERIAL);
    }

    @Override
    public void onCompletion(String result) {
        Material material = getMaterialArg();
        material.setAdded(true);
        String text = materialCount.getText().toString();
        int count = Integer.valueOf(text.isEmpty() ? "0" : text);
        material.setCount(count);
        EasyTimeManager.getInstance().updateMaterial(material);

        Log.d(TAG, String.format("completed: material = %s, count = %s", material.getName(), material.getCount()));
        Toast.makeText(getContext(), "Completed", Toast.LENGTH_SHORT).show();

        getMainActivity().jumpToRoot();
    }
}
