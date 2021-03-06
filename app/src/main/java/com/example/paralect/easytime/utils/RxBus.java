package com.example.paralect.easytime.utils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Oleg Tarashkevich on 16/01/2018.
 */

public class RxBus {

    private volatile static RxBus instance;

    /**
     * Returns singleton class instance
     */
    public static RxBus getInstance() {
        RxBus localInstance = instance;

        if (localInstance == null) {
            synchronized (RxBus.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RxBus();
                }
            }
        }
        return localInstance;
    }

    private RxBus() {
    }

    private final PublishSubject<Object> bus = PublishSubject.create();

    public void send(final Object event) {
        bus.onNext(event);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

    /**
     * Listen should return an Observable and not the publisher
     * Using ofType we filter only events that match that class type
     */
    public <T> Observable<T> listen(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public Observable<String> listen() {
        return bus.ofType(String.class);
    }

    public <T> void listen(Class<T> eventType, Observer<T> observer) {
         bus.ofType(eventType).subscribeWith(observer);
    }

    public static class Watcher<T> implements Observer<T> {

        protected Disposable disposable;

        public void subscribe(Class<T> eventType) {
            RxBus.getInstance().listen(eventType, this);
        }

        public void unSubscribe() {
            if (disposable != null)
                disposable.dispose();
        }

        @Override
        public void onSubscribe(Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(T t) {

        }

        @Override
        public void onError(Throwable e) {
           e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    }

}