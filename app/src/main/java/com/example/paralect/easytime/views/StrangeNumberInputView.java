package com.example.paralect.easytime.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.utils.ViewAnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 19/01/2018.
 */

public class StrangeNumberInputView extends LinearLayout implements View.OnFocusChangeListener, View.OnClickListener {

    private int mMaxInputNumber = 99;

    @BindView(R.id.strange_main_edit_text) EditText mMainTextView;
    @BindView(R.id.strange_details_edit_text) TextView mDetailsTextView;

    private OnChangeListener mOnChangeListener;

    public StrangeNumberInputView(Context context) {
        this(context, null);
    }

    public StrangeNumberInputView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrangeNumberInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    }

    public void setMaxInputNumber(int maxInputNumer) {
        mMaxInputNumber = maxInputNumer;
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

        if (mOnChangeListener != null)
            mOnChangeListener.onSelected(StrangeNumberInputView.this, hasFocus);

        if (hasFocus)
            mMainTextView.setText("00");
    }

    @Override
    public void onClick(View view) {
        requestFocus();
    }

    public void setOnSelectedChangeListener(OnChangeListener onChangeListener) {
        mOnChangeListener = onChangeListener;
    }
    // endregion

    public KeypadView.OnKeypadItemClickListener getKeypadItemClickListener() {
        return new KeypadView.OnKeypadItemClickListener() {

            @Override
            public void onNextClick() {
                if (mOnChangeListener != null)
                    mOnChangeListener.onCompleted();
            }

            @Override
            public void onDeleteClick() {
                String currentText = mMainTextView.getText().toString();

                if (currentText.length() == 2) {
                    int first = Character.getNumericValue(currentText.charAt(0));
                    int second = Character.getNumericValue(currentText.charAt(1));

                    String result = currentText;
                    if (first != 0 && second != 0) {
                        result = first + "" + 0;
                    } else if (first != 0 && second == 0) {
                        result = "00";
                    } else if (first == 0 && second != 0) {
                        result = first + "" + "0";
                    }

                    mMainTextView.setText(result);
                }
            }

            @Override
            public void onNumberClick(int number) {
                String currentText = mMainTextView.getText().toString();

                if (currentText.length() == 2) {
                    int first = Character.getNumericValue(currentText.charAt(0));
                    int second = Character.getNumericValue(currentText.charAt(1));

                    String result = currentText;
                    if (first == 0 && second == 0) {
                        result = first + "" + number;
                    } else if (first == 0 && second != 0) {
                        result = second + "" + number;
                        callOnEntered();
                    } else if (first != 0 && second == 0) {
                        result = first + "" + number;
                        callOnEntered();
                    } 

                    int resultNumber = Integer.parseInt(result);
                    if (resultNumber <= mMaxInputNumber)
                        mMainTextView.setText(result);
                    else {
                        mMainTextView.setText(String.valueOf(mMaxInputNumber));
                        ViewAnimationUtils.shakeAnimation(StrangeNumberInputView.this, 1000);
                    }
                }
            }

        };
    }

    private void callOnEntered() {
       if (mOnChangeListener != null)
           mOnChangeListener.onEntered(this);
    }

    public String getValue() {
        return mMainTextView.getText().toString();
    }

    public int getIntValue() {
        return Integer.valueOf(getValue());
    }

    public interface OnChangeListener {

        void onSelected(StrangeNumberInputView view, boolean isSelected);
        
        void onEntered(StrangeNumberInputView view);

        void onCompleted();
    }

}
