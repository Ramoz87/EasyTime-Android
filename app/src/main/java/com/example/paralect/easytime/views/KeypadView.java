package com.example.paralect.easytime.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 12.01.2018.
 */

public class KeypadView extends ExpandableLayout {
    private static final String TAG = KeypadView.class.getSimpleName();

    @BindView(R.id.keypadNext)
    View next;

    @BindView(R.id.keypadDelete)
    View delete;

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
        setExpanded(true, false);
    }
}
