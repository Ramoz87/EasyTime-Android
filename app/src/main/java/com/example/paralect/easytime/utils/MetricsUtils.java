package com.example.paralect.easytime.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by alexei on 29.01.2018.
 */

public final class MetricsUtils {

    private MetricsUtils() {

    }

    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
