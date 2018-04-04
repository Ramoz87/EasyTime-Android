package com.example.paralect.easytime.views.gallery;

import android.app.Activity;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.manager.entitysource.FileSource;
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
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.example.paralect.easytime.main.camera.CameraActivity.PICTURE_FILE_PATH;
import static com.example.paralect.easytime.model.Constants.REQUEST_CODE_CAMERA;

/**
 * Created by Oleg Tarashkevich on 16/01/2018.
 */

abstract class FilesPresenter<DATA, E> extends RxBus.Watcher<ResultEvent> implements IDataPresenter<DATA, E> {

    private IFilesView<DATA, E> mView;
    protected final FileSource fileSource = new FileSource();

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

            EasyImage.handleActivityResult(event.getRequestCode(), event.getResultCode(), event.getData(), activity, new DefaultCallback() {
                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    Logger.e(e);
                }

                @Override
                public void onImagePicked(java.io.File imageFile, EasyImage.ImageSource source, int type) {
                    String filePath = imageFile.getPath();
                    if (TextUtil.isNotEmpty(filePath))
                        onFilePathReceived(filePath);
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

    abstract void onFilePathReceived(String filePath);

    abstract void refreshData();

    protected void deleteFile(final File file) {
        if (file != null) {
            Completable completable = Completable.fromCallable(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    java.io.File imageFile = file.getImageFile();
                    boolean deleted = imageFile.delete();
                    fileSource.deleteFile(file);
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
