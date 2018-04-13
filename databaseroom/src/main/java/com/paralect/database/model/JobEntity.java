package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.paralect.database.converter.StringArrayConverter;

import static com.example.paralect.easytime.model.Constants.CUSTOMER_ID;
import static com.example.paralect.easytime.model.Constants.JOB_ID;
import static com.example.paralect.easytime.model.Constants.STATUS_ID;
import static com.example.paralect.easytime.model.Constants.TYPE_ID;

/**
 * Created by alexei on 26.12.2017.
 */

public class JobEntity {

    @ColumnInfo(name = "currency")
    private String currency;

    @ColumnInfo(name = CUSTOMER_ID)
    private String customerId;

    @ColumnInfo(name = "date")
    private long date;

    @ColumnInfo(name = "entityType")
    private String entityType = "job";

    @ColumnInfo(name = "information")
    private String information;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = JOB_ID)
    private String jobId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "number")
    private int number;

    @ColumnInfo(name = STATUS_ID)
    private String statusId;

    @ColumnInfo(name = TYPE_ID)
    private String typeId;

    @ColumnInfo(name = "discount")
    private int discount = 0; // default value

    @TypeConverters(StringArrayConverter.class)
    @ColumnInfo(name = "userIds")
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

    @NonNull
    public String getJobId() {
        return jobId;
    }

    public void setJobId(@NonNull String jobId) {
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

    public String[] getUserIds() {
        return userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }


}
