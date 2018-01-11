package com.example.paralect.easytime.main.customers.customer;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.CustomerContainer;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;

import java.util.ArrayList;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by Oleg Tarashkevich on 10/01/2018.
 */

public class CustomerPresenter implements IDataPresenter<CustomerContainer, Customer> {

    private IDataView<CustomerContainer> mDataView;

    @Override
    public IDataPresenter<CustomerContainer, Customer> setDataView(IDataView<CustomerContainer> view) {
        mDataView = view;
        return this;
    }

    @Override
    public IDataPresenter<CustomerContainer, Customer> requestData(final Customer customer) {
        Flowable<CustomerContainer> flowable = Flowable.create(new FlowableOnSubscribe<CustomerContainer>() {
            @Override
            public void subscribe(FlowableEmitter<CustomerContainer> emitter) throws Exception {
                try {
                    if (!emitter.isCancelled()) {
                        CustomerContainer container = new CustomerContainer();

                        ArrayList<Project> projects = (ArrayList<Project>) EasyTimeManager.getInstance().getProjects(customer);
                        ArrayList<Order> orders = (ArrayList<Order>) EasyTimeManager.getInstance().getOrders(customer);
                        ArrayList<Object> objects = (ArrayList<Object>) EasyTimeManager.getInstance().getObjects(customer);

                        customer.setAddress(Address.mock());
                        customer.setContacts(Contact.getMockContacts());

                        container.setCustomer(customer);
                        container.setProjects(projects);
                        container.setOrders(orders);
                        container.setObjects(objects);

                        emitter.onNext(container);
                        emitter.onComplete();
                    }
                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.LATEST);

        flowable.subscribe(new Consumer<CustomerContainer>() {
            @Override
            public void accept(CustomerContainer container) throws Exception {
                if (mDataView != null)
                    mDataView.onDataReceived(container);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
        return this;
    }

}
