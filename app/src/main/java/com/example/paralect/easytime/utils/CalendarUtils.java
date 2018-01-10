package com.example.paralect.easytime.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.paralect.easytime.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.annotations.NonNull;

/**
 * Created by alexei on 10.01.2018.
 */

public final class CalendarUtils {

    private CalendarUtils() {

    }

    public static String getDateString(int year, int month, int day) {
        Calendar calendar = getCalendar(year, month, day);
        return getDateString(calendar);
    }

    public static String getDateString(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d", Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static SpannableString getSpannableDateString(@NonNull Context context, Calendar calendar) {
        String space = "   ";
        String dateString = getDateString(calendar);
        SpannableString ss = new SpannableString(dateString + space);
        Drawable d = context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
        ss.setSpan(span, dateString.length(), dateString.length() + space.length(), 0);
        return ss;
    }

    public static SpannableString getSpannableDateString(@NonNull Context context, int year, int month, int day) {
        Calendar calendar = getCalendar(year, month, day);
        return getSpannableDateString(context, calendar);
    }

    public static Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar;
    }
}
