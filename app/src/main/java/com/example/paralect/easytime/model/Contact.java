package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

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

    public long getContactId() {
        return contactId;
    }

    public String getEmail() {
        return email;
    }

    public String getFax() {
        return fax;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public static List<Contact> getMockContacts(int size) {
        List<Contact> contacts = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Contact contact = new Contact();
            contact.contactId = i;
            contact.email = "mock_" + i + "@gmail.com";
            contact.fax = "+375 456 5464 4654";
            contact.firstName = "FirstName_" + i;
            contact.lastName = "LastName_" + i;
            contact.phone = "+37512345678" + i;
            contacts.add(contact);
        }

        return contacts;
    }
}
