package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

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
        object = in.readParcelable(Object.class.getClassLoader());
        order = in.readParcelable(Order.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(addressId);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(street);
        dest.writeString(zip);
        dest.writeParcelable(object, flags);
        dest.writeParcelable(order, flags);
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
}
