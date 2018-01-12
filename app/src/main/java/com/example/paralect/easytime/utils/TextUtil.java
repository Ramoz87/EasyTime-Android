package com.example.paralect.easytime.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ImageSpan;

/**
 * Created by Oleg Tarashkevich on 12/01/2018.
 */

public class TextUtil {

    public static SpannableString getSpannableDateString(@NonNull Context context, @NonNull String text, @DrawableRes int id) {
        String space = "   ";
        SpannableString ss = new SpannableString(space + text);
        try {
            Drawable d = ContextCompat.getDrawable(context, id);
            d.setBounds(0, 0, d.getIntrinsicWidth()/2, d.getIntrinsicHeight()/2);
            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
            ss.setSpan(span, 0, 1, 0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ss;
    }
}
