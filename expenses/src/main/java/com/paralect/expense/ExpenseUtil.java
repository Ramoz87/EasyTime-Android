package com.paralect.expense;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.paralect.expense.ExtendedExpense.Type.DRIVING;
import static com.paralect.expense.ExtendedExpense.Type.MATERIAL;
import static com.paralect.expense.ExtendedExpense.Type.OTHER;
import static com.paralect.expense.ExtendedExpense.Type.TIME;

/**
 * Created by Oleg Tarashkevich on 14/03/2018.
 */

public class ExpenseUtil {

    public static final String UNITY_PCS = "pcs";
    public static final String UNITY_KM = "km";
    public static final String UNITY_HOUR = "h";
    public static final String UNITY_MIN = "min";
    public static final String UNITY_CURRENCY = "CHF";

    public static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static String timeToString(long minutes) {
        String timeString = "";

        long hours = TimeUnit.MINUTES.toHours(minutes);
        minutes = minutes - TimeUnit.HOURS.toMinutes(hours);

        timeString += hours + UNITY_HOUR;
        timeString += " " + minutes + UNITY_MIN;

        return timeString;
    }

    @Nullable
    public static Date dateFromString(String dateString, SimpleDateFormat format) {
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getUnit(@ExtendedExpense.Type String type, UnitCallback callback) {
        String text = "";
        if (!TextUtils.isEmpty(type)) {

            switch (type) {
                case TIME:
                    text = callback.getTimeUnit();
                    break;

                case DRIVING:
                    text = callback.getDrivingUnit();
                    break;

                case OTHER:
                    text = callback.getOtherUnit();
                    break;

                case MATERIAL:
                    text = callback.getMaterialUnit();
                    break;
            }
        }
        return text;
    }

}
