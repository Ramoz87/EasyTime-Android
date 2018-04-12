package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static com.example.paralect.easytime.model.Constants.ADDRESS_ID;

/**
 * Created by alexei on 26.12.2017.
 */

@Entity(tableName = "Addresses")
public class AddressEntity {

    @PrimaryKey
    @ColumnInfo(name = ADDRESS_ID)
    private long addressId;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "street")
    private String street;
    @ColumnInfo(name = "zip")
    private String zip; // index ?
    @ColumnInfo(name = "customerId")
    private String customerId;

    public AddressEntity() {

    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
