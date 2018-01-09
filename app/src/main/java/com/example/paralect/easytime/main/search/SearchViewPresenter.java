package com.example.paralect.easytime.main.search;

import android.support.annotation.IdRes;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

/**
 * This class helps to retrieve data with a delay and asynchronous
 */
public abstract class SearchViewPresenter<DATA> implements ISearchViewPresenter<DATA> {

    private static final int DELAY = 500;
    private final PublishProcessor<String> mPublisher = PublishProcessor.create();
    protected ISearchView<DATA> mView;

    public SearchViewPresenter() {
        final Flowable<String> flowable = mPublisher.onBackpressureBuffer();
        flowable.debounce(DELAY, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String query) throws Exception {
                        requestData(query);
                    }
                });

    }

    @Override
    public void setupSearch(Menu menu, @IdRes int id, ISearchView<DATA> view) {
        mView = view;
        MenuItem searchItem = menu.findItem(id);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mPublisher.onNext(query);
                return true;
            }
        });
    }

}
