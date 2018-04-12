package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static com.example.paralect.easytime.model.Constants.CONTACT_ID;
import static com.example.paralect.easytime.model.Constants.CUSTOMER_ID;

/**
 * Created by alexei on 26.12.2017.
 */

@Entity(tableName = "contacts")
public class ContactEntity {

    @PrimaryKey
    @ColumnInfo(name = CONTACT_ID)
    private long contactId;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "fax")
    private String fax;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = CUSTOMER_ID)
    private String customerId;

    public ContactEntity() {

    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
