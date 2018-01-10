package com.example.paralect.easytime.main.search;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 10.01.2018.
 */

public final class SearchPresenterUtils {
    private static final int DEFAULT_DELAY = 200;

    private SearchPresenterUtils() {

    }

    public static <E> PublishProcessor<E> createPublisherProcessor(Consumer<E> consumer) {
        PublishProcessor<E> processor = PublishProcessor.create();
        final Flowable<E> flowable = processor.onBackpressureBuffer();
        int delay = DEFAULT_DELAY;
        flowable.debounce(delay, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
        return processor;
    }
}
