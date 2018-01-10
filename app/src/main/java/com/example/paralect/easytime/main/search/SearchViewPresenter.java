package com.example.paralect.easytime.main.search;

import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.paralect.easytime.main.IDataView;

import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

/**
 * This class helps to retrieve data with a delay and asynchronous
 */
public abstract class SearchViewPresenter<DATA> implements ISearchViewPresenter<DATA> {
    private PublishProcessor<String> mPublisher;
    protected IDataView<DATA> mView;

    // region Search
    private void setupPublisher() {
        if (mPublisher == null) {
            mPublisher = SearchPresenterUtils.createPublisherProcessor(new Consumer<String>() {
                @Override
                public void accept(String query) throws Exception {
                    requestData(query);
                }
            });
        }
    }

    @Override
    public SearchViewPresenter<DATA> setupSearch(Menu menu, int id) {
        setupPublisher();
        MenuItem searchItem = menu.findItem(id);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (mPublisher != null)
                    mPublisher.onNext(query);
                return true;
            }
        });
        return this;
    }
    // endregion

    @Override
    public SearchViewPresenter<DATA> setSearchDataView(IDataView<DATA> view) {
        mView = view;
        return this;
    }

}
