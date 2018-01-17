package com.example.paralect.easytime.views.gallery;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.utils.Logger;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Tarashkevich on 17/01/2018.
 */

final class ExpenseFilesPresenter extends FilesPresenter<Expense> {

    private Expense mExpense;

    @Override
    public IDataPresenter<List<File>, Expense> setDataView(IDataView<List<File>> view) {
        return this;
    }

    @Override
    public IDataPresenter<List<File>, Expense> requestData(final Expense expense) {
        if (expense != null) {
            mExpense = expense;
            requestData(new FlowableOnSubscribe<List<File>>() {
                @Override
                public void subscribe(FlowableEmitter<List<File>> emitter) throws Exception {
                    try {
                        if (!emitter.isCancelled()) {
                            List<File> files = EasyTimeManager.getInstance().getFiles(expense);
                            emitter.onNext(files);
                            emitter.onComplete();
                        }
                    } catch (Throwable e) {
                        emitter.onError(e);
                    }
                }
            });
        }
        return this;
    }

    @Override
    protected void onFileReceived(final java.io.File imageFile) {
        Completable completable = Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {

                File file = new File();
                file.setName("name");
                file.setFileUrl(imageFile.getPath());
                file.setExpensiveId(mExpense.getExpensiveId());
                file.setFileId(System.currentTimeMillis());

                EasyTimeManager.getInstance().saveFile(file);

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

    @Override
    protected void refreshFiles() {
        requestData(mExpense);
    }
}
