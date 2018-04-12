package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;

import static com.example.paralect.easytime.model.Constants.ADDRESS_ID;

/**
 * Created by alexei on 04.01.2018.
 */

public class JobWithAddressEntity extends JobEntity {

    @ColumnInfo(name = "addressString")
    private String addressString;

    @ColumnInfo(name = "cityString")
    private String cityString;

    @ColumnInfo(name = "zipString")
    private String zipString;

    @ColumnInfo(name = ADDRESS_ID)
    private long addressId;

    @Ignore
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
