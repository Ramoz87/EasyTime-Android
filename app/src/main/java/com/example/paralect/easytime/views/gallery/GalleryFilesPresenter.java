package com.example.paralect.easytime.views.gallery;

import android.app.Activity;

import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.event.ResultEvent;
import com.example.paralect.easytime.utils.IntentUtils;
import com.example.paralect.easytime.utils.RxBus;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by Oleg Tarashkevich on 16/01/2018.
 */

final class GalleryFilesPresenter extends RxBus.Observer<ResultEvent> {

    private IGalleryFilesView<List<File>> mView;

    void subscribe(){
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

    void setGalleryFilesView(IGalleryFilesView<List<File>> view) {
        mView = view;
    }

    void requestData(Job job) {
        Flowable<List<File>> flowable = Flowable.create(new FlowableOnSubscribe<List<File>>() {
            @Override
            public void subscribe(FlowableEmitter<List<File>> emitter) throws Exception {
                try {
                    if (!emitter.isCancelled()) {

                        EasyTimeManager.getInstance().getFiles()
                        customer.setAddress(Address.mock());
                        customer.setContacts(Contact.getMockContacts(4));

                        emitter.onNext(customer);
                        emitter.onComplete();
                    }
                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.LATEST);

        flowable.subscribe(new Consumer<List<File>>() {
            @Override
            public void accept(List<File> files) throws Exception {
                if (mView != null)
                    mView.onDataReceived(files);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });

    }

    void requestData(Expense expense) {

    }

}
