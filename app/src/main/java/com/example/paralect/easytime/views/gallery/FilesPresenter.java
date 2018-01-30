package com.example.paralect.easytime.views.gallery;

import android.app.Activity;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.model.event.ResultEvent;
import com.example.paralect.easytime.utils.IntentUtils;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.RxBus;
import com.example.paralect.easytime.utils.TextUtil;

import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.paralect.easytime.main.camera.CameraActivity.PICTURE_FILE_PATH;
import static com.example.paralect.easytime.model.Constants.REQUEST_CODE_CAMERA;

/**
 * Created by Oleg Tarashkevich on 16/01/2018.
 */

abstract class FilesPresenter<DATA, E> extends RxBus.Watcher<ResultEvent> implements IDataPresenter<DATA, E> {

    private IFilesView<DATA, E> mView;

    void subscribe() {
        subscribe(ResultEvent.class);
    }

    @Override
    public void onNext(ResultEvent event) {
        if (event.getResultCode() == Activity.RESULT_CANCELED) return;
        if (mView != null) {
            Activity activity = IntentUtils.getActivity(mView.getViewContext());
            if (!IntentUtils.isFinishing(activity) && event.getRequestCode() == REQUEST_CODE_CAMERA) {
                String filePath = event.getData().getStringExtra(PICTURE_FILE_PATH);
                if (TextUtil.isNotEmpty(filePath))
                    onFilePathReceived(filePath);
            }
        }
    }

    abstract void onFilePathReceived(String filePath);

    abstract void refreshData();

    protected void deleteFile(final File file) {
        if (file != null) {
            Completable completable = Completable.fromCallable(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    java.io.File imageFile = file.getImageFile();
                    boolean deleted = imageFile.delete();
                    EasyTimeManager.getInstance().deleteFile(file);
                    return null;
                }
            });

            completable
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            refreshData();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            Logger.e(throwable);
                        }
                    });
        }
    }

    void setGalleryFilesView(IFilesView<DATA, E> view) {
        mView = view;
    }

    protected void requestData(FlowableOnSubscribe<DATA> flowableOnSubscribe) {
        Flowable<DATA> flowable = Flowable.create(flowableOnSubscribe, BackpressureStrategy.LATEST);

        flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DATA>() {
                    @Override
                    public void accept(DATA data) {
                        onDataReceived(data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable);
                    }
                });
    }

    protected void onDataReceived(DATA data){
        if (mView != null)
            mView.onDataReceived(data);
    }
}
