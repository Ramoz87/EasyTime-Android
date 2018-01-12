package com.example.paralect.easytime.utils.anim;

import android.view.View;
import android.view.animation.Animation;

/**
 * Created by alexei on 12.01.2018.
 */

public final class AnimUtils {

    private AnimUtils() {

    }

    public static Animation.AnimationListener newAppearingAnimListener(final View view) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    public static Animation.AnimationListener newDisappearingAnimListener(final View view) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }
}
