package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

public class Customer implements Parcelable {

    private String companyName;
    private long customerId;
    private String firstName;
    private String lastName;

    private Address address;
    private List<Contact> contacts;

    public Customer() {

    }

    protected Customer(Parcel in) {
        companyName = in.readString();
        customerId = in.readLong();
        firstName = in.readString();
        lastName = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
        contacts = in.createTypedArrayList(Contact.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(companyName);
        dest.writeLong(customerId);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeParcelable(address, flags);
        dest.writeTypedList(contacts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
