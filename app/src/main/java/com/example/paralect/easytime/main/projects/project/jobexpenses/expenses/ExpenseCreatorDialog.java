package com.example.paralect.easytime.main.projects.project.jobexpenses.expenses;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.utils.TouchHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 16.01.2018.
 */

public class ExpenseCreatorDialog extends Dialog {

    @BindView(R.id.cancel) TextView cancel;
    @BindView(R.id.create) TextView create;
    @BindView(R.id.expenseName) EditText expenseName;

    private Listener listener;

    public interface Listener {
        void onCreate(ExpenseCreatorDialog dialog, String expenseName);
    }

    public ExpenseCreatorDialog(@NonNull Context context, @NonNull Listener listener) {
        super(context, true, null);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // remove empty title and divider line
        setContentView(R.layout.dialog_expense_creator);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        TouchHandler cancelAction = new TouchHandler() {
            @Override
            public void performClick(View v) {
                ExpenseCreatorDialog dialog = ExpenseCreatorDialog.this;
                dialog.cancel();
            }
        };
        cancel.setOnTouchListener(cancelAction);

        TouchHandler createAction = new TouchHandler() {
            @Override
            public void performClick(View v) {
                ExpenseCreatorDialog dialog = ExpenseCreatorDialog.this;
                dialog.cancel();
                dialog.listener.onCreate(dialog, expenseName.getText().toString());
            }
        };
        create.setOnTouchListener(createAction);
    }
}
