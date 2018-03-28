package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "Addresses")
public class AddressEntity {

    public static final String ID = "addressId";

    @DatabaseField(columnName = ID, generatedId = true)
    private String addressId;
    @DatabaseField(columnName = "city")
    private String city;
    @DatabaseField(columnName = "country")
    private String country;
    @DatabaseField(columnName = "street")
    private String street;
    @DatabaseField(columnName = "zip")
    private String zip; // index ?
    @DatabaseField(columnName = "customerId")
    private String customerId;

    public AddressEntity() {

    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
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
