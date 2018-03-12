package com.paralect.core;


import java.util.HashMap;
import java.util.Map;

public class Description {
    private Map<String, Object> data;

    public Description() {
        data = new HashMap<>();
    }

    public int getInt(String key) {
        return (int) data.get(key);
    }

    public long getLong(String key) {
        return (long) data.get(key);
    }

    public double getDouble(String key) {
        return (double) data.get(key);
    }

    public String getString(String key) {
        return (String) data.get(key);
    }

    public Object put(String key, Object obj) {
        return data.put(key, obj);
    }
}
