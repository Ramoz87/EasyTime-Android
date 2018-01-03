package com.example.paralect.easytime.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexei on 26.12.2017.
 */

public class Contact implements Parcelable {

    private long contactId;
    private String email;
    private String fax;
    private String firstName;
    private String lastName;
    private String phone;

    public Contact() {

    }

    protected Contact(Parcel in) {
        contactId = in.readLong();
        email = in.readString();
        fax = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(contactId);
        dest.writeString(email);
        dest.writeString(fax);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
