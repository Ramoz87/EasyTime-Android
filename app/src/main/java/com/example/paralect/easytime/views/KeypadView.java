package com.example.paralect.easytime.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.paralect.easytime.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 12.01.2018.
 */

public class KeypadView extends ExpandableLayout implements View.OnClickListener {
    private static final String TAG = KeypadView.class.getSimpleName();

    @BindView(R.id.keypadContent)
    GridLayout keypadContent;

    @BindView(R.id.keypadNext)
    View next;

    @BindView(R.id.keypadDelete)
    View delete;

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
            child.setOnClickListener(this);
            int id = child.getId();
            if (id == R.id.id0) setupNumberItem(child, 0);
            if (id == R.id.id1) setupNumberItem(child, 1);
            if (id == R.id.id2) setupNumberItem(child, 2);
            if (id == R.id.id3) setupNumberItem(child, 3);
            if (id == R.id.id4) setupNumberItem(child, 4);
            if (id == R.id.id5) setupNumberItem(child, 5);
            if (id == R.id.id6) setupNumberItem(child, 6);
            if (id == R.id.id7) setupNumberItem(child, 7);
            if (id == R.id.id8) setupNumberItem(child, 8);
            if (id == R.id.id9) setupNumberItem(child, 9);

        }
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "on click");
        if (onKeypadItemClickListener == null) return;

        Object tag = view.getTag();
        if (tag != null) { // we assume its a number item
            Log.d(TAG, "clicked view has some tag");
            TextView numberItem = (TextView) view;
            Integer number = (Integer) tag;
            onKeypadItemClickListener.onNumberClick(number);
        } else if (view.getId() == R.id.keypadNext) {
            onKeypadItemClickListener.onNextClick();
        } else if (view.getId() == R.id.keypadDelete) {
            onKeypadItemClickListener.onDeleteClick();
        }
    }

    private void setupNumberItem(View view, int number) {
        ((TextView) view).setText(String.valueOf(number));
        view.setTag(number);
    }
}
