package com.example.paralect.easytime;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.TypedValue;

/**
 * Created by alexei on 26.12.2017.
 */

public final class ContextUtils {

    private ContextUtils() {

    }

    public static @ColorInt int getAttrColor(@NonNull Context context, @AttrRes int attrId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrId, typedValue, true);
        return typedValue.data;
    }
}
