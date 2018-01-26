package com.example.paralect.easytime.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayout;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.utils.TouchHandler;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 12.01.2018.
 */

public class KeypadView extends ExpandableLayout {
    private static final String TAG = KeypadView.class.getSimpleName();

    @BindView(R.id.keypadContent) GridLayout keypadContent;
    @BindView(R.id.keypadNext) View next;
    @BindView(R.id.keypadDelete) View delete;
    @BindView(R.id.shadow) View shadow;

    private OnTouchListener getNewNumberHandler() {
        return new TouchHandler() {
            @Override
            public void performClick(View v) {
                Log.d(TAG, "clicked view has some tag");
                Integer number = (Integer) v.getTag();

                if (onKeypadItemClickListener != null) {
                    onKeypadItemClickListener.onNumberClick(number);
                }
            }
        };
    }

    private final OnTouchListener nextHandler = new TouchHandler() {
        @Override
        public void performClick(View v) {
            if (onKeypadItemClickListener != null) {
                onKeypadItemClickListener.onNextClick();
            }
        }
    };

    private final OnTouchListener deleteHandler = new TouchHandler() {
        @Override
        public void performClick(View v) {
            if (onKeypadItemClickListener != null) {
                onKeypadItemClickListener.onDeleteClick();
            }
        }
    };

    public void setOnKeypadItemClickListener(OnKeypadItemClickListener onKeypadItemClickListener) {
        this.onKeypadItemClickListener = onKeypadItemClickListener;
    }

    public interface OnKeypadItemClickListener {
        void onNextClick();
        void onDeleteClick();
        void onNumberClick(int number);
    }

    private OnKeypadItemClickListener onKeypadItemClickListener;

    public KeypadView(@NonNull Context context) {
        this(context, null);
    }

    public KeypadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeypadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public KeypadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);
        init();
    }

    private void init() {
        Log.d(TAG, "initializing");
        inflate(getContext(), R.layout.include_keypad_content, this);
        if (!isInEditMode()) {
            ButterKnife.bind(this);
        }
        initNumberItems();
        setExpanded(true, false);
    }

    private void initNumberItems() {
        int count = keypadContent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = keypadContent.getChildAt(i);
            int id = child.getId();
            if (id == R.id.id0) setupNumberItem(child, 0);
            else if (id == R.id.id1) setupNumberItem(child, 1);
            else if (id == R.id.id2) setupNumberItem(child, 2);
            else if (id == R.id.id3) setupNumberItem(child, 3);
            else if (id == R.id.id4) setupNumberItem(child, 4);
            else if (id == R.id.id5) setupNumberItem(child, 5);
            else if (id == R.id.id6) setupNumberItem(child, 6);
            else if (id == R.id.id7) setupNumberItem(child, 7);
            else if (id == R.id.id8) setupNumberItem(child, 8);
            else if (id == R.id.id9) setupNumberItem(child, 9);
            else if (id == R.id.keypadNext) {
                child.setOnTouchListener(nextHandler);
            } else if (id == R.id.keypadDelete) {
                child.setOnTouchListener(deleteHandler);
            }
        }
    }


    private void setupNumberItem(View view, int number) {
        ((TextView) view).setText(String.valueOf(number));
        view.setTag(number);
        view.setOnTouchListener(getNewNumberHandler());
    }

    public View getShadowView() {
        return shadow;
    }

    @Override
    public void setExpanded(boolean expand, boolean animate) {
        if (expand == isExpanded()) return;

        if (animate) {
            Log.d(TAG, String.format("animate fading %s", expand ? "in" : "out"));
            int duration = getDuration();
            float start = expand ? 0 : 1;
            float end = start == 0 ? 1 : 0;
            ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(this, View.ALPHA, start, end);
            alphaAnimation.setDuration(duration);
            alphaAnimation.start();
        }
        super.setExpanded(expand, animate);
    }
}
