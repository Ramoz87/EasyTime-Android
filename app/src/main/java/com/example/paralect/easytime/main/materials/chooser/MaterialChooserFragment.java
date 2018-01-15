package com.example.paralect.easytime.main.materials.chooser;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.FragmentNavigator;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.utils.CalendarUtils;

import java.util.List;
import java.util.SortedMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 04.01.2018.
 */

public class MaterialChooserFragment extends AbsStickyFragment implements IDataView<SortedMap<Character,List<Material>>> {

    private MaterialChooserPresenter presenter = new MaterialChooserPresenter();
    private MaterialAlphabetAdapter adapter = new MaterialAlphabetAdapter();
    private FragmentNavigator navigator;

    public static MaterialChooserFragment newInstance() {
        return new MaterialChooserFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (FragmentNavigator) context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setDataView(this)
                .requestData(null);
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        return adapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        presenter.setDataView(this)
                .setupQuerySearch((SearchView) menu.findItem(R.id.item_search).getActionView())
                .requestData(new String[]{"", null});
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Fragment fragment = MaterialEditorFragment.newInstance(adapter.getItem(i));
        navigator.pushFragment(fragment);
    }
}
