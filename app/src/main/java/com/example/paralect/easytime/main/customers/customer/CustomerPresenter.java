package com.example.paralect.easytime.main.customers.customer;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.JobsContainer;
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

final class CustomerPresenter implements IDataPresenter<Customer, Customer>, ICustomerPresenter<JobsContainer, Customer> {

    private IDataView<Customer> customerDataView;
    private ICustomerDataView<JobsContainer> jobsDataView;

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
                        customer.setContacts(Contact.getMockContacts());

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

    // region Get Jobs
    @Override
    public ICustomerPresenter<JobsContainer, Customer> setJobsDataView(ICustomerDataView<JobsContainer> view) {
        jobsDataView = view;
        return this;
    }

    @Override
    public ICustomerPresenter<JobsContainer, Customer> requestForJobs(final Customer customer) {
        Flowable<JobsContainer> flowable = Flowable.create(new FlowableOnSubscribe<JobsContainer>() {
            @Override
            public void subscribe(FlowableEmitter<JobsContainer> emitter) throws Exception {
                try {
                    if (!emitter.isCancelled()) {
                        JobsContainer container = new JobsContainer();

                        ArrayList<Project> projects = (ArrayList<Project>) EasyTimeManager.getInstance().getProjects(customer);
                        ArrayList<Order> orders = (ArrayList<Order>) EasyTimeManager.getInstance().getOrders(customer);
                        ArrayList<Object> objects = (ArrayList<Object>) EasyTimeManager.getInstance().getObjects(customer);

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

        flowable.subscribe(new Consumer<JobsContainer>() {
            @Override
            public void accept(JobsContainer container) throws Exception {
                if (jobsDataView != null)
                    jobsDataView.onJobsReceived(container);
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
