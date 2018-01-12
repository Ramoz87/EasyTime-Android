package com.example.paralect.easytime.main.materials.chooser;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.views.KeypadView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 12.01.2018.
 */

public class MaterialEditorFragment extends BaseFragment implements KeypadView.OnKeypadItemClickListener {

    @BindView(R.id.keypad) KeypadView keypadView;

    public static MaterialEditorFragment newInstance() {
        return new MaterialEditorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_material_editor, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        keypadView.setOnKeypadItemClickListener(this);
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

    @Override
    public void onNextClick() {
        Toast.makeText(getContext(), "Clicked on next", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick() {
        Toast.makeText(getContext(), "Clicked on delete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNumberClick(int number) {
        Toast.makeText(getContext(), "Clicked on " + number, Toast.LENGTH_SHORT).show();
    }
}
