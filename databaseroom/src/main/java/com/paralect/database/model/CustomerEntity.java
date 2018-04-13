package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

import static com.example.paralect.easytime.model.Constants.COMPANY_NAME;
import static com.example.paralect.easytime.model.Constants.CUSTOMER_ID;

/**
 * Created by alexei on 26.12.2017.
 */

@Entity(tableName = "customers")
public class CustomerEntity {

    @ColumnInfo(name = COMPANY_NAME)
    private String companyName;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = CUSTOMER_ID)
    private String customerId;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "addressId")
    private long addressId;

    @Ignore
    private AddressEntity address;
    @Ignore
    private List<ContactEntity> contacts;

    public CustomerEntity() {

    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @NonNull
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(@NonNull String customerId) {
        this.customerId = customerId;
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

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public List<ContactEntity> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactEntity> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
}
