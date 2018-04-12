package com.paralect.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.paralect.easytime.model.Constants.CONTACT_ID;
import static com.example.paralect.easytime.model.Constants.CUSTOMER_ID;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "contacts")
public class ContactEntity {

    @DatabaseField(columnName = CONTACT_ID, generatedId = true)
    private long contactId;

    @DatabaseField(columnName = "email")
    private String email;

    @DatabaseField(columnName = "fax")
    private String fax;

    @DatabaseField(columnName = "firstName")
    private String firstName;

    @DatabaseField(columnName = "lastName")
    private String lastName;

    @DatabaseField(columnName = "phone")
    private String phone;

    @DatabaseField(columnName = CUSTOMER_ID)
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
