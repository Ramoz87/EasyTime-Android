package com.example.paralect.easytime.main.projects.project;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.ProjectType;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.RxBus;

import java.util.List;
import java.util.SortedMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by alexei on 23.01.2018.
 */

class InformationPresenter extends RxBus.Watcher<String> implements IDataPresenter<List<Type>, Void> {
    private static final String TAG = InformationPresenter.class.getSimpleName();

    private InformationView<List<Type>> view;
    private String mDate;

    @Override
    public IDataPresenter<List<Type>, Void> setDataView(IDataView<List<Type>> view) {
        this.view = (InformationView)view;
        return this;
    }

    @Override
    public InformationPresenter requestData(Void v) {
        Observable<List<Type>> observable = Observable.create(new ObservableOnSubscribe<List<Type>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Type>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        Logger.d(TAG, "performing request");
                        List<Type> types = EasyTimeManager.getInstance().getStatuses();
                        emitter.onNext(types);
                        emitter.onComplete();
                    }

                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Type>>() {
                    @Override
                    public void onNext(List<Type> types) {
                        if (view != null) {
                            Logger.d(TAG, "request performed, passing data");
                            view.onDataReceived(types);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return this;
    }

    void requestObjects(final Job job){
        if (job == null) return;

        @ProjectType.Type int type = job.getProjectType();
        if (type != ProjectType.Type.TYPE_PROJECT) return;

        Observable<List<Object>> observable = Observable.create(new ObservableOnSubscribe<List<Object>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Object>> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {

                        Project project = (Project)job;
                        String[] jobIds = project.getObjectIds();
                        List<Object> objects = EasyTimeManager.getInstance().getObjects(jobIds);
                        emitter.onNext(objects);
                        emitter.onComplete();
                    }

                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Object>>() {
                    @Override
                    public void onNext(List<Object> map) {
                        if (view != null)
                            view.onObjectsReceived(map);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    // region RxBus subscription for event of date changing
    void subscribe() {
        subscribe(String.class);
    }

    @Override
    public void onNext(String date) {
        mDate = date;
    }

    public String getDate() {
        return mDate;
    }
    // endregion

}
