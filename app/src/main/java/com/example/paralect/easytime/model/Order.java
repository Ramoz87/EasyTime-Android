package com.example.paralect.easytime.model;

import android.os.Parcel;

import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

public class Order extends Job {

    private String contact;
    private String deliveryTime;
    private List<Object> objects;
    private Address deliveryAddress;

    public Order() {

    }

    protected Order(Parcel in) {
        super(in);
    }
}
