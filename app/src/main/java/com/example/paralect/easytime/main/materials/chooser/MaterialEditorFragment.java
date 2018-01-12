package com.example.paralect.easytime.main.materials.chooser;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by alexei on 12.01.2018.
 */

public class MaterialEditorFragment extends BaseFragment {

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
}
