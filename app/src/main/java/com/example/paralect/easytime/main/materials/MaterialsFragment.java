package com.example.paralect.easytime.main.materials;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.main.materials.chooser.MaterialChooserFragment;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.utils.VerticalDividerItemDecoration;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 26.12.2017.
 */

public class MaterialsFragment extends BaseFragment implements IDataView<List<Material>> {
    private static final String TAG = MaterialsFragment.class.getSimpleName();

    private MaterialsPresenter presenter = new MaterialsPresenter();
    private MaterialAdapter adapter = new MaterialAdapter();

    @BindView(R.id.list)
    EmptyRecyclerView list;

    @BindView(R.id.placeholder)
    View placeholder;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @OnClick(R.id.fab)
    void onFabClick(FloatingActionButton fab) {
        Log.d(TAG, "on fab click");
        Fragment chooser = MaterialChooserFragment.newInstance();
        getMainActivity().getFragmentNavigator().pushFragment(chooser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static MaterialsFragment newInstance() {
        return new MaterialsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_materials, parent, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "on view created");
        ButterKnife.bind(this, view);
        init();
        updateMyMaterials();
    }

    private void init() {
        list.setEmptyView(placeholder);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        int color = ContextCompat.getColor(getContext(), R.color.list_divider_color);
        int height = getResources().getInteger(R.integer.list_divider_height);
        RecyclerView.ItemDecoration decor = new VerticalDividerItemDecoration(color, height);
        list.addItemDecoration(decor);
    }

    private void initActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setTitle(R.string.nav_materials);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        initActionBar(actionBar);
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataReceived(List<Material> materials) {
        int count = materials.size();
        Log.d(TAG, String.format("received %s material%s", count, count == 0 ? "" : "s"));
        adapter.setData(materials);
    }

    private void updateMyMaterials() {
        presenter.setDataView(this)
                .requestData(null);
    }
}
