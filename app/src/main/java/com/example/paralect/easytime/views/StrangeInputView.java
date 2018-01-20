package com.example.paralect.easytime.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
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

public class StrangeInputView extends LinearLayout implements View.OnFocusChangeListener, View.OnClickListener, TextWatcher {

    private int mMaxInputSize = 2;

    @BindView(R.id.strange_main_edit_text) EditText mMainTextView;
    @BindView(R.id.strange_details_edit_text) TextView mDetailsTextView;

    private OnSelectedChangeListener mOnSelectedChangeListener;

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
        mMainTextView.setInputType(InputType.TYPE_NULL);

        setOnFocusChangeListener(this);
        mMainTextView.setOnFocusChangeListener(this);
        mDetailsTextView.setOnFocusChangeListener(this);

        setOnClickListener(this);
        mMainTextView.setOnClickListener(this);
        mDetailsTextView.setOnClickListener(this);

        mMainTextView.addTextChangedListener(this);
    }

    public void setMaxInputSize(int maxInputSize) {
        mMaxInputSize = maxInputSize;
        mMainTextView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxInputSize)});
    }

    public void setMainText(CharSequence text) {
        mMainTextView.setText(text);
    }

    public void setDetailsText(CharSequence text) {
        mDetailsTextView.setText(text);
    }

    public void setDetailsText(@StringRes int resid) {
        mDetailsTextView.setText(resid);
    }

    public EditText getMainTextView() {
        return mMainTextView;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        setSelected(hasFocus);

        if (mOnSelectedChangeListener != null)
            mOnSelectedChangeListener.onSelected(StrangeInputView.this, hasFocus);
    }

    @Override
    public void onClick(View view) {
        requestFocus();
    }

    public void setOnSelectedChangeListener(OnSelectedChangeListener onSelectedChangeListener) {
        mOnSelectedChangeListener = onSelectedChangeListener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    // region Text Watcher
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
//         String receivedText = s.toString();
//         String actualText = mMainTextView.getText().toString();
//
//         int length = receivedText.length();
//         if (length == 0){
//             mMainTextView.setText("00");
//         }else if (length == 1){
//             mMainTextView.setText("0" + receivedText);
//         }else if (length > 2){
//             mMainTextView.setText(receivedText.substring(0, 1));
//         }

    }

    public interface OnSelectedChangeListener {
        void onSelected(StrangeInputView view, boolean isSelected);
    }
    // endregion

}
