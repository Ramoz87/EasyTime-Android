package com.example.paralect.easytime.utils;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by alexei on 12.01.2018.
 */

public abstract class TouchHandler implements View.OnTouchListener {

    private boolean touchedAlready = false;
    private View touchedView = null;
    private Rect touchedRect = null;

    private Drawable old;
    private int normalColor;
    private int touchedColor;

    public static final int TOUCH_DOWN = Color.rgb(211,211,211);
    public static final int TOUCH_UP = Color.WHITE;

    public TouchHandler() {
        this(TOUCH_DOWN);
    }

    public TouchHandler(int touchedColor) {
        this.touchedColor = touchedColor;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (touchedView != null && view != touchedView)
            return true;
        int action = motionEvent.getAction();
        if (action == MotionEvent.ACTION_DOWN
                && !touchedAlready) {
            onTouchDown(view, touchedColor);
        } else if (action == MotionEvent.ACTION_UP
                && view == touchedView) {
            onTouchUp(view);
            performClick(view);// perform click
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (!touchedRect
                    .contains(view.getLeft() + (int) motionEvent.getX(), view.getTop() + (int) motionEvent.getY())) {
                onTouchUp(view);
            }
        }
        return true;
    }

    private void onTouchDown(View view, int color) {
        old = view.getBackground();
        if (old == null || !(old instanceof ColorDrawable)) {
            normalColor = Color.TRANSPARENT;
        } else {
            normalColor = ((ColorDrawable) old).getColor();
        }
        touchedView = view;
        touchedAlready = true;
        touchedRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.setBackgroundColor(color);
    }

    private void onTouchUp(View view) {
        touchedAlready = false;
        touchedView = null;
        //view.setBackground(old);
        view.setBackgroundColor(normalColor);
    }

    public abstract void performClick(View v);
}
