package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by alexei on 04.01.2018.
 */

public class JobWithAddressEntity extends JobEntity {

    @DatabaseField(columnName = "addressString")
    private String addressString;

    @DatabaseField(columnName = "cityString")
    private String cityString;

    @DatabaseField(columnName = "zipString")
    private String zipString;

    @DatabaseField(columnName = "addressId")
    private long addressId;

    private AddressEntity address;

    public JobWithAddressEntity() {

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

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}