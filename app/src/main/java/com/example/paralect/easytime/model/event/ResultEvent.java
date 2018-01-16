package com.example.paralect.easytime.model.event;

import android.content.Intent;

/**
 * Created by Oleg Tarashkevich on 16/01/2018.
 */

public class ResultEvent {

    private int requestCode;
    private int resultCode;
    private Intent data;

    public ResultEvent(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getData() {
        return data;
    }
}
