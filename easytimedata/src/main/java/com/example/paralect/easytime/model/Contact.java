package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by alexei on 26.12.2017.
 */

public class Contact implements Parcelable {

    private String contactId;
    private String email;
    private String fax;
    private String firstName;
    private String lastName;
    private String phone;
    private String customerId;

    public Contact() {

    }

    protected Contact(Parcel in) {
        contactId = in.readString();
        email = in.readString();
        fax = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        phone = in.readString();
        customerId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contactId);
        dest.writeString(email);
        dest.writeString(fax);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phone);
        dest.writeString(customerId);
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

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactId() {
        return contactId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public static List<Contact> getMockContacts(int size) {
        List<Contact> contacts = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Contact contact = new Contact();
            contact.contactId = UUID.randomUUID().toString();
            contact.email = "mock_" + i + "@gmail.com";
            contact.fax = "+375 456 5464 4654";
            contact.firstName = "Brad";
            contact.lastName = "Pitt " + i;
            contact.phone = "+37512345678" + i;
            contacts.add(contact);
        }

        return contacts;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
