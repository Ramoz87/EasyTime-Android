package com.example.paralect.easytime.main.materials.chooser;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.MaterialComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by alexei on 04.01.2018.
 */

public class MaterialChooserFragment extends BaseFragment implements IDataView<SortedMap<Character,List<Material>>>, MaterialAlphabetAdapter.OnCheckedChangeListener {

    @BindView(R.id.sticky_list_headers_list_view)
    StickyListHeadersListView listView;

    @BindView(R.id.addMaterials)
    Button addMaterials;

    @OnClick(R.id.addMaterials)
    void addMaterials(Button button) {
        updateMyMaterials(materialsToUpdate);
    }

    private MaterialChooserPresenter presenter = new MaterialChooserPresenter();
    private MaterialAlphabetAdapter adapter = new MaterialAlphabetAdapter();
    private List<Material> materialsToUpdate = new ArrayList<>();

    public static MaterialChooserFragment newInstance() {
        return new MaterialChooserFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_material_chooser, parent, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        MaterialAlphabetAdapter adapter = this.adapter;
        adapter.setOnCheckedChangeListener(this);
        listView.setAdapter(adapter);
        presenter.setDataView(this)
                .requestData(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        presenter.setDataView(this)
                .setupQuerySearch((SearchView) menu.findItem(R.id.item_search).getActionView())
                .requestData(new String[]{""});
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
    public void onDataReceived(SortedMap<Character, List<Material>> materials) {
        for (Character c : materials.keySet()) {
            List<Material> list = materials.get(c);
            for (Material updatable : materialsToUpdate) {
                for (Material material : list) {
                    if (material.equals(updatable)) {
                        material.setAdded(updatable.isAdded());
                        break;
                    }
                }
            }
        }
        adapter.setData(materials);
    }

    private void updateMyMaterials(final List<Material> materials) {
        Observable<List<Material>> observable = Observable.create(new ObservableOnSubscribe<List<Material>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Material>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        for (Material material : materials) {
                            if (material.isAdded()) {
                                material.setStockQuantity(1);
                            } else {
                                material.setStockQuantity(0);
                            }
                            EasyTimeManager.getInstance().updateMaterial(material);
                        }
                        emitter.onNext(materials);
                        emitter.onComplete();
                    }

                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Material>>() {
                    @Override
                    public void onNext(List<Material> materials1) {
                        MaterialChooserFragment.this.getMainActivity().jumpToRoot();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onCheckedChange(Material material, boolean isChecked) {
        if (materialsToUpdate.contains(material)) {
            materialsToUpdate.remove(material);
        } else {
            material.setAdded(isChecked);
            materialsToUpdate.add(material);
        }
    }
}
