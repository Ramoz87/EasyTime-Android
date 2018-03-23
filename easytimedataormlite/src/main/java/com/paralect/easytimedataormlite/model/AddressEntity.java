package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "Addresses")
public class AddressEntity {

    @DatabaseField(columnName = "addressId", generatedId = true)
    private long addressId;
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

    private CustomerEntity customer;
    private ObjectEntity object;
    private OrderEntity order;

    public AddressEntity() {

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

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public ObjectEntity getObject() {
        return object;
    }

    public void setObject(ObjectEntity object) {
        this.object = object;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
