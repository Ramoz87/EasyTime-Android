package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.paralect.datasource.core.Entity;

import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "customers")
public class Customer implements Entity<String> {

    @DatabaseField(columnName = "companyName")
    private String companyName;

    @DatabaseField(columnName = "customerId", id = true)
    private String customerId;

    @DatabaseField(columnName = "firstName")
    private String firstName;

    @DatabaseField(columnName = "lastName")
    private String lastName;

    @DatabaseField(columnName = "addressId")
    private long addressId;

    private Address address;
    private List<Contact> contacts;

    public Customer() {

    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getId() {
        return customerId;
    }

    public void setId(String customerId) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
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
