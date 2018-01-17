package com.example.paralect.easytime.views.gallery;

import android.app.Activity;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.model.event.ResultEvent;
import com.example.paralect.easytime.utils.IntentUtils;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.RxBus;

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
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

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
                EasyImage.handleActivityResult(event.getRequestCode(), event.getResultCode(), event.getData(), activity, new DefaultCallback() {
                    @Override
                    public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onImagePicked(java.io.File imageFile, EasyImage.ImageSource source, int type) {
                        onFileReceived(imageFile);
                    }

                    @Override
                    public void onCanceled(EasyImage.ImageSource source, int type) {
                        //Cancel handling, you might wanna remove taken photo if it was canceled
                        if (source == EasyImage.ImageSource.CAMERA) {
                            java.io.File photoFile = EasyImage.lastlyTakenButCanceledPhoto(mView.getViewContext());
                            if (photoFile != null) photoFile.delete();
                        }
                    }
                });
            }
        }
    }

    protected abstract void onFileReceived(java.io.File imageFile);

    protected abstract void refreshFiles();

    void deleteFile(final File file){
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
