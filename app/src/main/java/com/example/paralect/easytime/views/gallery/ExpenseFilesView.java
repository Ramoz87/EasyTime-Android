package com.example.paralect.easytime.views.gallery;

import android.content.Context;
import android.util.AttributeSet;

import com.example.paralect.easytime.model.Expense;

/**
 * Created by Oleg Tarashkevich on 17/01/2018.
 */

public class ExpenseFilesView extends FilesView<Expense> {

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
    protected FilesPresenter getFilesPresenter() {
        return presenter;
    }

    @Override
    public void setupWithEntity(Expense entity) {
        presenter.requestData(entity);
    }
}
