package com.example.paralect.easytime.main.projects.project.invoice;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.utils.TouchHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 16.01.2018.
 */

public class DiscountDialog extends Dialog {

    @BindView(R.id.cancel) TextView cancel;
    @BindView(R.id.create) TextView create;
    @BindView(R.id.expenseName) EditText expenseName;

    private Listener listener;

    public interface Listener {
        void onCreateNewExpenseTemplate(DiscountDialog dialog, String expenseName);
    }

    public DiscountDialog(@NonNull Context context, @NonNull Listener listener) {
        super(context, true, null);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // remove empty title and divider line
        setContentView(R.layout.dialog_discount);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        TouchHandler cancelAction = new TouchHandler() {
            @Override
            public void performClick(View v) {
                DiscountDialog dialog = DiscountDialog.this;
                dialog.cancel();
            }
        };
        cancel.setOnTouchListener(cancelAction);

        TouchHandler createAction = new TouchHandler() {
            @Override
            public void performClick(View v) {
                DiscountDialog dialog = DiscountDialog.this;
                dialog.cancel();
                dialog.listener.onCreateNewExpenseTemplate(dialog, expenseName.getText().toString());
            }
        };
        create.setOnTouchListener(createAction);

        expenseName.requestFocus();
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); // simulate click on edit text

        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        setCanceledOnTouchOutside(false);

//        ViewGroup.LayoutParams params = this.getWindow().getAttributes();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        this.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
