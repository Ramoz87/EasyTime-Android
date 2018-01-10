package com.example.paralect.easytime.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by alexei on 10.01.2018.
 */

public final class CalendarUtils {

    private CalendarUtils() {

    }

    public static String getDateString(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d", Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }
}
