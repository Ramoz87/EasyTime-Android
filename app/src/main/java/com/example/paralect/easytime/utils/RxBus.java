package com.example.paralect.easytime.utils;

import io.reactivex.Observable;
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

    public <T> Disposable listen(Class<T> eventType, DisposableObserver<T> observer) {
        return bus.ofType(eventType).subscribeWith(observer);
    }

    public static class Observer<T> extends DisposableObserver<T> {

        protected Disposable disposable;
        private EventListener<T> eventListener;

        public void setEventListener(EventListener<T> eventListener) {
            this.eventListener = eventListener;
        }

        public void subscribe(Class<T> eventType) {
            disposable = RxBus.getInstance().listen(eventType, this);
        }

        public void unSubscribe() {
            if (disposable != null)
                disposable.dispose();
        }

        @Override
        public void onNext(T t) {
           if (eventListener != null)
               eventListener.onEventReceived(t);
        }

        @Override
        public void onError(Throwable e) {
           e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }

        public interface EventListener<T> {
            void onEventReceived(T event);
        }
    }

}