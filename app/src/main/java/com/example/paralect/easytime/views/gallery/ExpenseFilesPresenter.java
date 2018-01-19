package com.example.paralect.easytime.views.gallery;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.utils.CollectionUtils;
import com.example.paralect.easytime.utils.Logger;

import java.util.ArrayList;
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

final class ExpenseFilesPresenter extends FilesPresenter<Void> {

    private final List<File> files = new ArrayList<>();

    @Override
    public IDataPresenter<List<File>, Void> setDataView(IDataView<List<File>> view) {
        return this;
    }

    @Override
    public IDataPresenter<List<File>, Void> requestData(final Void parameter) {
        requestData(new FlowableOnSubscribe<List<File>>() {
            @Override
            public void subscribe(FlowableEmitter<List<File>> emitter) throws Exception {
                try {
                    if (!emitter.isCancelled()) {
                        emitter.onNext(files);
                        emitter.onComplete();
                    }
                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        });
        return this;
    }

    @Override
    protected void onFilePathReceived(final String filePath) {
        Completable completable = Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                // save temporary file
                File file = new File();
                file.setName("name");
                file.setFileUrl(filePath);
                files.add(file);
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
        requestData((Void)null);
    }

    @Override
    protected void deleteFile(File file) {
        files.clear();
        super.deleteFile(file);
    }

    /**
     * Set expenseId to File
     * @param expense
     */
    public void setExpense(Expense expense){
        try {
            File file = CollectionUtils.getFirst(files);
            if (file != null) {
                file.setExpensiveId(expense.getExpenseId());
                file = EasyTimeManager.getInstance().saveFileAndGet(file);
                files.clear();
                files.add(file);
            }
            Logger.d("file saved");
        } catch (Throwable e) {
            Logger.e(e);
        }
    }

    public boolean isFileSaved(){
        boolean isSaved = false;
        File file = CollectionUtils.getFirst(files);
        if (file != null)
            isSaved = file.isSaved();
        return isSaved;
    }
}
