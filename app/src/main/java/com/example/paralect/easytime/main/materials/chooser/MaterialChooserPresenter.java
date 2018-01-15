package com.example.paralect.easytime.main.materials.chooser;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.MaterialComparator;
import com.example.paralect.easytime.utils.Sorter;

import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

public class MaterialChooserPresenter extends SearchViewPresenter<SortedMap<Character, List<Material>>> {

    private Comparator<Material> comparator = new MaterialComparator();
    private final Sorter<Material> sorter = new Sorter<Material>() {
        @Override
        public char getCharacterForItem(Material item) {
            return item.getName().charAt(0);
        }
    };

    @Override
    public MaterialChooserPresenter requestData(final String[] parameters) {
        Observable<SortedMap<Character, List<Material>>> observable = Observable.create(new ObservableOnSubscribe<SortedMap<Character, List<Material>>>() {
            @Override
            public void subscribe(ObservableEmitter<SortedMap<Character, List<Material>>> emitter) throws Exception {
                try {

                    if (!emitter.isDisposed()) {
                        String query = parameters != null && parameters.length > 0 ? parameters[0] : null;
                        List<Material> materials = EasyTimeManager.getInstance().getMaterials();
                        // split list of materials alphabetically
                        SortedMap<Character, List<Material>> map = sorter.getSortedItems(materials, comparator);
                        emitter.onNext(map);
                        emitter.onComplete();
                    }

                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SortedMap<Character, List<Material>>>() {
                    @Override
                    public void onNext(SortedMap<Character, List<Material>> map) {
                        if (mView != null)
                            mView.onDataReceived(map);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return this;
    }

}
