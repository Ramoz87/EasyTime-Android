package com.example.paralect.easytime.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.paralect.easytime.R;

/**
 * Created by alexei on 16.01.2018.
 */

public class CongratulationsActivity extends Activity implements Runnable {

    private static final int DELAY = 2000;

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, CongratulationsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_congratulations);

        Handler handler = new Handler();
        handler.postDelayed(this, DELAY);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void run() {
        finish();
    }
}
