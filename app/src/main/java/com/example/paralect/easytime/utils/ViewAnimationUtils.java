package com.example.paralect.easytime.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by alexei on 28.12.2017.
 */

public final class ViewAnimationUtils {

    private ViewAnimationUtils() {

    }

    public static void expand(final View view) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        view.getLayoutParams().height = 1;
        view.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp / ms
        long duration = (int) (targetHeight / view.getContext().getResources().getDisplayMetrics().density);
        duration = 200;
        a.setDuration(duration);
        view.startAnimation(a);
    }

    public static void collapse(final View view) {
        final int initialHeight = view.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp / ms
        long duration = (int) (initialHeight / view.getContext().getResources().getDisplayMetrics().density);
        duration = 200;
        a.setDuration(duration);
        view.startAnimation(a);
    }

    public static void layoutWithAnimation(final View view, boolean collapse) {
        if (!collapse) {
            ValueAnimator va = ValueAnimator.ofInt(100, 200);
            va.setDuration(400);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    view.getLayoutParams().height = (Integer) animation.getAnimatedValue();
                    view.requestLayout();
                }
            });
            va.start();
            collapse = true;
        } else {
            ValueAnimator va = ValueAnimator.ofInt(200, 100);
            va.setDuration(400);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    view.getLayoutParams().height = (Integer) animation.getAnimatedValue();
                    view.requestLayout();
                }
            });
            va.start();
            collapse = false;
        }
    }

    private void animateViewCollapsing(final View view) {
        final ViewGroup.LayoutParams lp = view.getLayoutParams();
        final int originalHeight = view.getHeight();

        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 0);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                lp.height = 0;
                view.setLayoutParams(lp);
                view.setVisibility(View.GONE);
            }

            @SuppressLint("NewApi")
            @Override
            public void onAnimationCancel(Animator arg0) {

            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @SuppressLint("NewApi")
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                view.setLayoutParams(lp);
            }
        });
        animator.start();
    }

    public static void shakeAnimation(View v) {
        shakeAnimation(v, 1000);
    }

    public static void shakeAnimation(View v, long duration) {
        ObjectAnimator shakeAnimator = ObjectAnimator.ofFloat(v, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
        shakeAnimator.setDuration(duration);
        shakeAnimator.start();
    }
}
