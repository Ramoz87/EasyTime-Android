package com.paralect.database.converter;

import android.arch.persistence.room.TypeConverter;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Oleg Tarashkevich on 12/04/2018.
 */

public class StringArrayConverter {

    @TypeConverter
    public static String[] stringToArray(String data) {
        if (data == null) {
            return new String[]{};
        }

        try {
            JSONArray jsonArray = new JSONArray(data);
            String[] values = new String[jsonArray.length()];
            for (int i = 0; i < values.length; i++) {
                values[i] = jsonArray.optString(i);
            }
            return values;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new String[]{};
    }

    @TypeConverter
    public static String arrayToString(String[] values) {
        if (values == null) return null;
        JSONArray jsonArray = new JSONArray();
        for (String value : values)
            jsonArray.put(value);
        return jsonArray.toString();
    }

}