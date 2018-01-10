package com.example.paralect.easytime.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.paralect.easytime.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by alexei on 10.01.2018.
 */

public final class CalendarUtils {

    private static Random random = new Random();

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

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

    public static Calendar nextCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2018 + random.nextInt(2));
        calendar.set(Calendar.MONTH, random.nextInt(12));
        calendar.set(Calendar.DAY_OF_MONTH, random.nextInt(30));
        return calendar;
    }

    public static Date nextDate() {
        return nextCalendar().getTime();
    }

    @Nullable
    public static Date dateFromString(String dateString, SimpleDateFormat format) {
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String stringFromDate(Date date, SimpleDateFormat format) {
        return format.format(date);
    }
}
