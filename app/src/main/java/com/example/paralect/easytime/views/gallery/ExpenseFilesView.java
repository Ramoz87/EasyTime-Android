package com.example.paralect.easytime.views.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Expense;

import butterknife.BindView;

/**
 * Created by Oleg Tarashkevich on 17/01/2018.
 */

public class ExpenseFilesView extends FilesView<Void> {

    @BindView(R.id.gallery_capture_button) View captureButton;

    private ExpenseFilesPresenter presenter = new ExpenseFilesPresenter();

    public ExpenseFilesView(Context context) {
        super(context);
    }

    public ExpenseFilesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpenseFilesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        captureButton.setVisibility(GONE);
    }

    @Override
    protected FilesPresenter<Void> getFilesPresenter() {
        return presenter;
    }

    @Override
    public void setupWithEntity(Void entity) {
        presenter.requestData(entity);
    }

    public void setExpense(Expense expense){
        presenter.setExpense(expense);
    }
    /**
     * Remove the file if it has no expenseId
     */
    public void checkFile(){
        if (!presenter.isFileSaved())
            deleteSelectedFile();
    }

}
