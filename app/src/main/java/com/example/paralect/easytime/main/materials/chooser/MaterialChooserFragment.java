package com.example.paralect.easytime.main.materials.chooser;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.model.Material;

import java.util.List;
import java.util.SortedMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 04.01.2018.
 */

public class MaterialChooserFragment extends AbsStickyFragment implements IDataView<SortedMap<Character,List<Material>>> {

    private MaterialChooserPresenter presenter = new MaterialChooserPresenter();
    private MaterialAlphabetAdapter adapter = new MaterialAlphabetAdapter();

    public static MaterialChooserFragment newInstance() {
        return new MaterialChooserFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setDataView(this)
                .requestData("");
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        return adapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        actionBar.setTitle("Materials");
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public void onDataReceived(SortedMap<Character, List<Material>> map) {
       adapter.setData(map);
    }
}
