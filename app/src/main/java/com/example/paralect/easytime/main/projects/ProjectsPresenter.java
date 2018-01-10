package com.example.paralect.easytime.main.projects;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.search.SearchViewPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.ViewUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

public class ProjectsPresenter extends SearchViewPresenter<List<Job>> {

    @Override
    public ProjectsPresenter requestData(final String query) {
        Observable<List<Job>> observable = Observable.create(new ObservableOnSubscribe<List<Job>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Job>> emitter) throws Exception {
                try {

                    if (!emitter.isDisposed()) {
                        List<Job> jobs = EasyTimeManager.getJobs(EasyTimeApplication.getContext(), query);
                        emitter.onNext(jobs);
                        emitter.onComplete();
                    }

                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Job>>() {
                    @Override
                    public void onNext(List<Job> jobs) {
                        if (mView != null)
                            mView.onDataReceived(jobs);
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

    public SpannableString getTitle(String date){
        String space = "   ";
        SpannableString ss = new SpannableString(date + space);
        Drawable d = ContextCompat.getDrawable(EasyTimeApplication.getContext(), R.drawable.ic_small_arrow_down);
//        Drawable d = ContextCompat.getDrawable(EasyTimeApplication.getContext(), R.drawable.ic_arrow_drop_down_black_24dp);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = ViewUtils.getImageSpan(d);
        ss.setSpan(span, date.length(), date.length() + space.length(), 0);
        return ss;
    }
}
