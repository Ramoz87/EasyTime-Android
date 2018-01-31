package com.example.paralect.easytime.main.projects.project.invoice;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.views.KeypadEditorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 31/01/2018.
 */

public class DiscountActivity extends AppCompatActivity {

    @BindView(R.id.cancel) TextView cancel;
    @BindView(R.id.create) TextView create;
    @BindView(R.id.expenseName) EditText expenseName;
    @BindView(R.id.keypadEditorView) KeypadEditorView keypad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.dark_blue));
        }

        setContentView(R.layout.dialog_discount);
        ButterKnife.bind(this);

        if (!keypad.isExpanded()) {
            keypad.expand();
        }
    }
}
