package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import static com.example.paralect.easytime.model.Constants.CUSTOMER_ID;
import static com.example.paralect.easytime.model.Constants.JOB_ID;
import static com.example.paralect.easytime.model.Constants.STATUS_ID;
import static com.example.paralect.easytime.model.Constants.TYPE_ID;

/**
 * Created by alexei on 26.12.2017.
 */

public class JobEntity {

    @DatabaseField(columnName = "currency")
    private String currency;

    @DatabaseField(columnName = CUSTOMER_ID)
    private String customerId;

    @DatabaseField(columnName = "date")
    private long date;

    @DatabaseField(columnName = "entityType")
    private String entityType = "job";

    @DatabaseField(columnName = "information")
    private String information;

    @DatabaseField(columnName = JOB_ID, id = true)
    private String jobId;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "number")
    private int number;

    @DatabaseField(columnName = STATUS_ID)
    private String statusId;

    @DatabaseField(columnName = TYPE_ID)
    private String typeId;

    @DatabaseField(columnName = "discount")
    private int discount = 0; // default value

    @DatabaseField(columnName = "userIds", dataType = DataType.SERIALIZABLE)
    private String[] userIds;

    public JobEntity() {

    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getId() {
        return jobId;
    }

    public void setId(String jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getNumberWithName(){
        return number + ": " + name;
    }

    @Override
    public String toString() {
        return entityType + " " + name + " | " + information;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String[] getMemberIds() {
        return userIds;
    }

    public void setMemberIds(String[] userIds) {
        this.userIds = userIds;
    }


}
