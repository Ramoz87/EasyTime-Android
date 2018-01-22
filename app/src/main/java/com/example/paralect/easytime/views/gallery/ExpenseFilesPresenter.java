package com.example.paralect.easytime.views.gallery;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.utils.Logger;

/**
 * Created by Oleg Tarashkevich on 17/01/2018.
 */

final class ExpenseFilesPresenter extends FilesPresenter<File, Expense> {

    private File file;

    @Override
    public IDataPresenter<File, Expense> setDataView(IDataView<File> view) {
        return this;
    }

    @Override
    public IDataPresenter<File, Expense> requestData(final Expense parameter) {
        return this;
    }

    @Override
    protected void onFilePathReceived(final String filePath) {
        file = new File();
        file.setName("name");
        file.setFileUrl(filePath);
        refreshData();
    }

    @Override
    void refreshData() {
        onDataReceived(file);
    }

    void deleteFile() {
        deleteFile(file);
    }

    /**
     * Set expenseId to File
     *
     * @param expense
     */
    void setExpense(Expense expense) {
        try {
            if (file != null) {
                file.setExpenseId(expense.getExpenseId());
                file = EasyTimeManager.getInstance().saveFileAndGet(file);
            }
            Logger.d("file saved");
        } catch (Throwable e) {
            Logger.e(e);
        }
    }

    boolean isFileSaved() {
        boolean isSaved = false;
        if (file != null)
            isSaved = file.isSaved();
        return isSaved;
    }
}
