package com.example.paralect.easytime.views.gallery;

import android.content.Context;
import android.util.AttributeSet;

import com.example.paralect.easytime.model.Job;

/**
 * Created by Oleg Tarashkevich on 17/01/2018.
 */

public class JobFilesView extends FilesView<Job> {

    private JobFilesPresenter presenter = new JobFilesPresenter();

    public JobFilesView(Context context) {
        super(context);
    }

    public JobFilesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JobFilesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected FilesPresenter<Job> getFilesPresenter() {
        return presenter;
    }

    @Override
    public void setupWithEntity(Job entity) {
        presenter.requestData(entity);
    }
}
