package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by alexei on 26.12.2017.
 */

public class Address implements Parcelable {

    private long addressId;
    private String city;
    private String country;
    private String street;
    private String zip; // index ?
    private String customerId;

    private Customer customer;
    private Object object;
    private Order order;

    public Address() {

    }

    protected Address(Parcel in) {
        addressId = in.readLong();
        city = in.readString();
        country = in.readString();
        street = in.readString();
        zip = in.readString();
        customer = in.readParcelable(Customer.class.getClassLoader());
        object = in.readParcelable(Object.class.getClassLoader());
        order = in.readParcelable(Order.class.getClassLoader());
        customerId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(addressId);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(street);
        dest.writeString(zip);
        dest.writeParcelable(customer, flags);
        dest.writeParcelable(object, flags);
        dest.writeParcelable(order, flags);
        dest.writeString(customerId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        boolean hasItem = false;
        if (!TextUtils.isEmpty(country)) {
            sb.append(country);
            hasItem = true;
        }
        if (!TextUtils.isEmpty(city)) {
            if (hasItem) sb.append(", ");
            sb.append(city);
            hasItem = true;
        }
        if (!TextUtils.isEmpty(street)) {
            if (hasItem) sb.append(", ");
            sb.append(street);
            hasItem = true;
        }
        if (!TextUtils.isEmpty(zip)) {
            if (hasItem) sb.append(", ");
            sb.append(zip);
            hasItem = true;
        }
        return sb.toString();
    }

    public String getQueryAddress() {
        StringBuilder builder = new StringBuilder();
        String street = this.street.replaceAll("[0-9]", "").trim();
        builder.append("geo:0,0?q=");
        if (!TextUtils.isEmpty(country))
            builder.append("+").append(country);
        if (!TextUtils.isEmpty(city))
            builder.append("+").append(city);
        if (!TextUtils.isEmpty(street))
            builder.append("+").append(street);
        return builder.toString();
    }

    public boolean hasAnyAddress(){
        return !TextUtils.isEmpty(country) || !TextUtils.isEmpty(city) || !TextUtils.isEmpty(street);
    }

    public static Address mock() {
        Address address = new Address();
        address.addressId = 456;
        address.country = "Belarus";
        address.city = "Minsk";
        address.street = "Nekrasova";
        return address;
    }

    @Override
    public String toString() {
        return city + " " + street;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
