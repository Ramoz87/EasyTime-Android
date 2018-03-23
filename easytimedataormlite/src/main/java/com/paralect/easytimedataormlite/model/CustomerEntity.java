package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.paralect.datasource.core.Entity;

import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "customers")
public class CustomerEntity implements Entity<String> {

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

    private AddressEntity address;
    private List<ContactEntity> contacts;

    public CustomerEntity() {

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
