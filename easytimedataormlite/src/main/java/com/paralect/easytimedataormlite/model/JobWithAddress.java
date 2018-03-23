package com.paralect.easytimedataormlite.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by alexei on 04.01.2018.
 */

public class JobWithAddress extends Job  {

    @DatabaseField(columnName = "addressString")
    private String addressString;

    @DatabaseField(columnName = "cityString")
    private String cityString;

    @DatabaseField(columnName = "zipString")
    private String zipString;

    @DatabaseField(columnName = "addressId")
    private long addressId;

    private Address address;

    public JobWithAddress() {

    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getCityString() {
        return cityString;
    }

    public void setCityString(String cityString) {
        this.cityString = cityString;
    }

    public String getZipString() {
        return zipString;
    }

    public void setZipString(String zipString) {
        this.zipString = zipString;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
