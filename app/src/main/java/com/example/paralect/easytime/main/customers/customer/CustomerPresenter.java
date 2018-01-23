package com.example.paralect.easytime.main.customers.customer;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.Customer;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by Oleg Tarashkevich on 10/01/2018.
 */

final class CustomerPresenter implements IDataPresenter<Customer, Customer> {

    private IDataView<Customer> customerDataView;

    // region Refresh Customer
    @Override
    public IDataPresenter<Customer, Customer> setDataView(IDataView<Customer> view) {
        customerDataView = view;
        return this;
    }

    @Override
    public IDataPresenter<Customer, Customer> requestData(final Customer customer) {
        Flowable<Customer> flowable = Flowable.create(new FlowableOnSubscribe<Customer>() {
            @Override
            public void subscribe(FlowableEmitter<Customer> emitter) throws Exception {
                try {
                    if (!emitter.isCancelled()) {

                        customer.setAddress(Address.mock());
                        customer.setContacts(Contact.getMockContacts(4));

                        emitter.onNext(customer);
                        emitter.onComplete();
                    }
                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.LATEST);

        flowable.subscribe(new Consumer<Customer>() {
            @Override
            public void accept(Customer customer) throws Exception {
                if (customerDataView != null)
                    customerDataView.onDataReceived(customer);
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
