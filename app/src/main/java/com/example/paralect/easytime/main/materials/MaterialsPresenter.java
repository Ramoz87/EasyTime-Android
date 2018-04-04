package com.example.paralect.easytime.main.materials;

import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.manager.entitysource.MaterialsSource;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.utils.Logger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 15.01.2018.
 */

public class MaterialsPresenter extends SearchViewPresenter<List<Material>> {

    private final MaterialsSource materialsSource = new MaterialsSource();

    @Override
    public MaterialsPresenter requestData(final String[] parameters) {
        Observable<List<Material>> observable = Observable.create(new ObservableOnSubscribe<List<Material>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Material>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        List<Material> materials = materialsSource.getMyMaterials();
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
                    public void onNext(List<Material> materials) {
                        if (mView != null)
                            mView.onDataReceived(materials);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return this;
    }
}
