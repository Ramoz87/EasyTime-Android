package com.example.paralect.easytime.main.customers.customer;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.CustomerContainer;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.utils.MiscUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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
    public IDataPresenter<CustomerContainer, Customer> requestData(Customer parameter) {
        Flowable<CustomerContainer> flowable = Flowable.create(new FlowableOnSubscribe<CustomerContainer>() {
            @Override
            public void subscribe(FlowableEmitter<CustomerContainer> emitter) throws Exception {
               try{
                   if (!emitter.isCancelled()){
                       CustomerContainer container = new CustomerContainer();
                       emitter.onNext(container);
                       emitter.onComplete();
                   }
               } catch (Throwable e){
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

            }
        });
        return this;
    }

}
