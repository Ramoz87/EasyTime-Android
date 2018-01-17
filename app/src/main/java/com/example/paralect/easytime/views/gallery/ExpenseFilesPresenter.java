package com.example.paralect.easytime.views.gallery;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.File;

import java.util.List;

import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

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
    protected void onFileReceived(java.io.File imageFile) {
      // save file and retrieve from db
    }
}
