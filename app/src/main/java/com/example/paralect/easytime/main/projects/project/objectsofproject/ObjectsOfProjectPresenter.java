package com.example.paralect.easytime.main.projects.project.objectsofproject;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.ObjectComparator;
import com.example.paralect.easytime.model.Object;
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
 * Created by alexei on 17.01.2018.
 */

public class ObjectsOfProjectPresenter extends SearchViewPresenter<SortedMap<Character, List<Object>>> {

    private Comparator<Object> comparator = new ObjectComparator();
    private final Sorter<Object> sorter = new Sorter<Object>() {
        @Override
        public char getCharacterForItem(Object item) {
            String companyName = item.getName();
            return companyName.charAt(0);
        }
    };

    @Override
    public IDataPresenter<SortedMap<Character, List<Object>>, String[]> requestData(final String[] parameters) {
        Observable<SortedMap<Character, List<Object>>> observable = Observable.create(new ObservableOnSubscribe<SortedMap<Character, List<Object>>>() {
            @Override
            public void subscribe(ObservableEmitter<SortedMap<Character, List<Object>>> emitter) throws Exception {
                try {

                    if (!emitter.isDisposed()) {
                        List<Object> objects = EasyTimeManager.getInstance().getObjects(parameters);
                        // split list of objects alphabetically
                        SortedMap<Character, List<Object>> map = sorter.getSortedItems(objects, comparator);
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
                .subscribe(new DisposableObserver<SortedMap<Character, List<Object>>>() {
                    @Override
                    public void onNext(SortedMap<Character, List<Object>> map) {
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
