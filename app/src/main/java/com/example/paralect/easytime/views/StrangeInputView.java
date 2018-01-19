package com.example.paralect.easytime.views;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 19/01/2018.
 */

public class StrangeInputView extends LinearLayout implements View.OnFocusChangeListener {

    private int mMaxInputSize = 2;

    @BindView(R.id.strange_main_edit_text) EditText mainTextView;
    @BindView(R.id.strange_details_edit_text) TextView detailsTextView;

    @BindColor(R.color.blue) int blueColor;
    @BindColor(R.color.gray) int grayColor;

    public StrangeInputView(Context context) {
        this(context, null);
    }

    public StrangeInputView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrangeInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_strange_input, this);
        if (!isInEditMode()) {
            ButterKnife.bind(this);
        }
        mainTextView.setOnFocusChangeListener(this);
        mainTextView.setInputType(InputType.TYPE_NULL);
    }

    public void setMaxInputSize(int maxInputSize) {
        mMaxInputSize = maxInputSize;
        mainTextView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxInputSize)});
    }

    public void setMainText(CharSequence text) {
        mainTextView.setText(text);
    }

    public void setDetailsText(CharSequence text) {
        detailsTextView.setText(text);
    }

    public void setDetailsText(@StringRes int resid) {
        detailsTextView.setText(resid);
    }

    public EditText getMainTextView() {
        return mainTextView;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        setSelected(hasFocus);
        if (hasFocus) {
            mainTextView.setTextColor(blueColor);
            detailsTextView.setTextColor(blueColor);
        } else {
            mainTextView.setTextColor(grayColor);
            detailsTextView.setTextColor(grayColor);
        }
    }


}
