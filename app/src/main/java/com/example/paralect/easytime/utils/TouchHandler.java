package com.example.paralect.easytime.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.paralect.easytime.R;

/**
 * Created by alexei on 12.01.2018.
 */

public abstract class TouchHandler implements View.OnTouchListener {

    private boolean touchedAlready = false;
    private View touchedView = null;
    private Rect touchedRect = null;
    private int normalColor;
    private ValueAnimator colorAnimation;

    public TouchHandler() {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (touchedView != null && view != touchedView)
            return true;
        int action = motionEvent.getAction();
        if (action == MotionEvent.ACTION_DOWN
                && !touchedAlready) {
            onTouchDown(view);
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

    private void onTouchDown(final View view) {
        Drawable old = view.getBackground();
        if (old == null || !(old instanceof ColorDrawable)) {
            normalColor = Color.TRANSPARENT;
        } else {
            normalColor = ((ColorDrawable) old).getColor();
        }
        @ColorInt int colorFrom = ContextCompat.getColor(view.getContext(), R.color.pressed);
        @ColorInt int colorTo = ContextCompat.getColor(view.getContext(), R.color.long_pressed);
        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(150); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
        touchedView = view;
        touchedAlready = true;
        touchedRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        // view.setBackgroundColor(touchedColor);
    }

    private void onTouchUp(View view) {
        touchedAlready = false;
        touchedView = null;
        //view.setBackground(old);
        if (colorAnimation != null) colorAnimation.cancel();
        view.setBackgroundColor(normalColor);
    }

    public abstract void performClick(View v);
}
