package com.example.paralect.easytime.main.customers.customer;

import android.util.Pair;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.entitysource.AddressSource;
import com.example.paralect.easytime.manager.entitysource.ContactSource;
import com.example.paralect.easytime.manager.entitysource.JobSource;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.Customer;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by Oleg Tarashkevich on 10/01/2018.
 */

final class CustomerPresenter implements IDataPresenter<Pair<Customer, List<Integer>>, Customer> {

    private IDataView<Pair<Customer, List<Integer>>> customerDataView;
    private final ContactSource contactSource = new ContactSource();
    private final AddressSource addressSource = new AddressSource();
    private final JobSource jobSource = new JobSource();

    // region Refresh CustomerEntity
    @Override
    public IDataPresenter<Pair<Customer, List<Integer>>, Customer> setDataView(IDataView<Pair<Customer, List<Integer>>> view) {
        customerDataView = view;
        return this;
    }

    @Override
    public IDataPresenter<Pair<Customer, List<Integer>>, Customer> requestData(final Customer customer) {
        Flowable<Pair<Customer, List<Integer>>> flowable = Flowable.create(new FlowableOnSubscribe<Pair<Customer, List<Integer>>>() {
            @Override
            public void subscribe(FlowableEmitter<Pair<Customer, List<Integer>>> emitter) throws Exception {
                try {
                    if (!emitter.isCancelled()) {
                        List<Contact> contacts = contactSource.getContacts(customer);
                        Address address = addressSource.getAddress(customer);
                        customer.setAddress(address);
                        customer.setContacts(contacts);
                        List<Integer> types = jobSource.getJobTypes(customer);
                        emitter.onNext(new Pair<>(customer, types));
                        emitter.onComplete();
                    }
                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.LATEST);

        flowable.subscribe(new Consumer<Pair<Customer, List<Integer>>>() {
            @Override
            public void accept(Pair<Customer, List<Integer>> pair) throws Exception {
                if (customerDataView != null)
                    customerDataView.onDataReceived(pair);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
        return this;
    }
    // endregion
}
