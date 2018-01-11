package com.example.paralect.easytime.main.customers;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.CustomerComparator;
import com.example.paralect.easytime.utils.Sorter;

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

public class CustomersPresenter extends SearchViewPresenter<SortedMap<Character, List<Customer>>> {

    private final CustomerComparator comparator = new CustomerComparator();
    private final Sorter<Customer> sorter = new Sorter<Customer>() {
        @Override
        public char getCharacterForItem(Customer item) {
            String companyName = item.getCompanyName();
            return companyName.charAt(0);
        }
    };

    @Override
    public CustomersPresenter requestData(final String query, final String date) {
        Observable<SortedMap<Character, List<Customer>>> observable = Observable.create(new ObservableOnSubscribe<SortedMap<Character, List<Customer>>>() {
            @Override
            public void subscribe(ObservableEmitter<SortedMap<Character, List<Customer>>> emitter) throws Exception {
                try {

                    if (!emitter.isDisposed()) {
                        List<Customer> customers = EasyTimeManager.getInstance().getCustomers(query);
                        // split list of customers alphabetically
                        SortedMap<Character, List<Customer>> map = sorter.getSortedItems(customers, comparator);
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
                .subscribe(new DisposableObserver<SortedMap<Character, List<Customer>>>() {
                    @Override
                    public void onNext(SortedMap<Character, List<Customer>> map) {
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
