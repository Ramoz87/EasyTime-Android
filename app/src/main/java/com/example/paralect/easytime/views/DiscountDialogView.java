package com.example.paralect.easytime.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paralect.easytime.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 01/02/2018.
 */

public class DiscountDialogView extends CardView {

    @BindView(R.id.save_button) Button saveButton;
    @BindView(R.id.cancel_button) Button canelButton;
    @BindView(R.id.discount_dialog_edit_text) EditText editText;

    public DiscountDialogView(@NonNull Context context) {
        this(context, null);
    }

    public DiscountDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscountDialogView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.dialog_discount, this);
        ButterKnife.bind(this);
        editText.setInputType(InputType.TYPE_NULL);
    }

    public EditText geteditText() {
        return editText;
    }

    public TextView getSaveButton() {
        return saveButton;
    }

    public TextView getCanelButton() {
        return canelButton;
    }

    public boolean isVisible(){
        return getVisibility() == VISIBLE;
    }

}
