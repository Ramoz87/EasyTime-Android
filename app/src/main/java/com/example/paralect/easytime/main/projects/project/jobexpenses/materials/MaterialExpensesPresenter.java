package com.example.paralect.easytime.main.projects.project.jobexpenses.materials;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Material;

import java.util.List;
import java.util.SortedMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 17.01.2018.
 */

public class MaterialExpensesPresenter extends SearchViewPresenter<List<Material>> {
    @Override
    public IDataPresenter<List<Material>, String[]> requestData(String[] parameter) {
        Observable<List<Material>> observable = Observable.create(new ObservableOnSubscribe<List<Material>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Material>> emitter) throws Exception {
                try {

                    if (!emitter.isDisposed()) {
                        List<Material> materials = EasyTimeManager.getInstance().getMyMaterials();
                        // split list of customers alphabetically
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
                    public void onNext(List<Material> list) {
                        if (mView != null)
                            mView.onDataReceived(list);
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
