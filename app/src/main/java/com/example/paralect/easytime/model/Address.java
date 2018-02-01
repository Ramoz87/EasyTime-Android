package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.TextUtils;

import com.example.paralect.easytime.EasyTimeApplication;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.utils.TextUtil;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "Addresses")
public class Address implements Parcelable {

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

    public SpannableString getFullAddress() {
        StringBuilder sb = new StringBuilder();
        boolean hasItem = false;
        if (TextUtil.isNotEmpty(country)) {
            sb.append(country);
            hasItem = true;
        }
        if (TextUtil.isNotEmpty(city)) {
            if (hasItem) sb.append(", ");
            sb.append(city);
            hasItem = true;
        }
        if (TextUtil.isNotEmpty(street)) {
            if (hasItem) sb.append(", ");
            sb.append(street);
            hasItem = true;
        }
        String params = sb.toString();
        return TextUtil.getSpannableDateString(EasyTimeApplication.getContext(), params, R.drawable.ic_checkpoint);
    }

    public String getQueryAddress() {
        StringBuilder builder = new StringBuilder();
        builder.append("geo:0,0?q=");
        if (TextUtil.isNotEmpty(country))
            builder.append("+").append(country);
        if (TextUtil.isNotEmpty(city))
            builder.append("+").append(city);
        if (TextUtil.isNotEmpty(street))
            builder.append("+").append(street);
        return builder.toString();
    }

    public boolean hasAnyAddress(){
        return TextUtil.isNotEmpty(country) || TextUtil.isNotEmpty(city) || TextUtil.isNotEmpty(street);
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
