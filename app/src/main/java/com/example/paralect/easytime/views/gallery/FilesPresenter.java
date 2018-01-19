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

import java.util.List;
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

/**
 * Created by Oleg Tarashkevich on 16/01/2018.
 */

abstract class FilesPresenter<E> extends RxBus.Observer<ResultEvent> implements IDataPresenter<List<File>, E> {

    private IFilesView<List<File>, E> mView;

    void subscribe() {
        subscribe(ResultEvent.class);
    }

    @Override
    public void onNext(ResultEvent event) {
        if (mView != null) {
            Activity activity = IntentUtils.getActivity(mView.getViewContext());
            if (!IntentUtils.isFinishing(activity)) {
                String filePath = event.getData().getStringExtra(PICTURE_FILE_PATH);
                if (TextUtil.isNotEmpty(filePath))
                    onFilePathReceived(filePath);
            }
        }
    }

    protected abstract void onFilePathReceived(String filePath);

    protected abstract void refreshFiles();

    protected void deleteFile(final File file) {
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
                        refreshFiles();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable);
                    }
                });
    }

    void setGalleryFilesView(IFilesView<List<File>, E> view) {
        mView = view;
    }

    protected void requestData(FlowableOnSubscribe<List<File>> flowableOnSubscribe) {
        Flowable<List<File>> flowable = Flowable.create(flowableOnSubscribe, BackpressureStrategy.LATEST);

        flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(List<File> files) {
                        if (mView != null)
                            mView.onDataReceived(files);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable);
                    }
                });
    }

}
