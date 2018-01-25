package com.example.paralect.easytime.utils.anim;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.paralect.easytime.R;

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

    public static void performReincarnation(@NonNull final ImageView imageView, final Drawable afterReincarnation, int durToHide, int durToShow, int delay) {
        Context context = imageView.getContext();
        final Animation inc = AnimationUtils.loadAnimation(context, R.anim.full_inc);
        final Animation dec = AnimationUtils.loadAnimation(context, R.anim.full_dec);
        dec.setDuration(durToHide);
        inc.setDuration(durToShow);
        dec.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setImageDrawable(afterReincarnation);
                imageView.startAnimation(inc);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        dec.setStartOffset(delay);
        imageView.startAnimation(dec);
    }

    public static void performReincarnation(@NonNull final ImageView imageView, @DrawableRes int drawableId, int durToHide, int durToShow, int delay) {
        Context context = imageView.getContext();
        Drawable afterReincarnation = ContextCompat.getDrawable(context, drawableId);
        performReincarnation(imageView, afterReincarnation, durToHide, durToShow, delay);
    }

    public static void hideWithAnimation(@NonNull View view, int duration, int delay) {
        Context context = view.getContext();
        final Animation dec = AnimationUtils.loadAnimation(context, R.anim.full_dec);
        dec.setAnimationListener(newDisappearingAnimListener(view));
        dec.setDuration(duration);
        dec.setStartOffset(delay);
        view.startAnimation(dec);
    }

    public static void showWithAnimation(@NonNull View view, int duration, int delay) {
        Context context = view.getContext();
        final Animation inc = AnimationUtils.loadAnimation(context, R.anim.full_inc);
        inc.setAnimationListener(newAppearingAnimListener(view));
        inc.setDuration(duration);
        inc.setStartOffset(delay);
        view.startAnimation(inc);
    }
}
