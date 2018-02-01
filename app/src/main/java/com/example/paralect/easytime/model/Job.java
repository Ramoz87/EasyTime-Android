package com.example.paralect.easytime.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by alexei on 26.12.2017.
 */

public class Job implements Parcelable, ProjectType {

    public static final String TAG = Job.class.getSimpleName();

    @DatabaseField(columnName = "currency")
    private String currency;

    @DatabaseField(columnName = "customerId")
    private String customerId;

    @DatabaseField(columnName = "date")
    private String date;

    @DatabaseField(columnName = "entityType")
    private String entityType = "job";

    @DatabaseField(columnName = "information")
    private String information;

    @DatabaseField(columnName = "jobId", id = true)
    private String jobId;

    private Object members;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "number")
    private int number;

    @DatabaseField(columnName = "statusId")
    private String statusId;

    @DatabaseField(columnName = "typeId")
    private String typeId;

    @DatabaseField(columnName = "discount")
    private int discount = 0; // default value

    private File image;
    private Customer customer;
    private com.example.paralect.easytime.model.Type status;

    public Job() {

    }

    protected Job(Parcel in) {
        currency = in.readString();
        customerId = in.readString();
        date = in.readString();
        entityType = in.readString();
        information = in.readString();
        jobId = in.readString();
        // members = in.readParcelable(Object.class.getClassLoader());
        name = in.readString();
        number = in.readInt();
        statusId = in.readString();
        typeId = in.readString();
        discount = in.readInt();
        customer = in.readParcelable(Customer.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currency);
        dest.writeString(customerId);
        dest.writeString(date);
        dest.writeString(entityType);
        dest.writeString(information);
        dest.writeString(jobId);
        // dest.writeParcelable(members, flags);
        dest.writeString(name);
        dest.writeInt(number);
        dest.writeString(statusId);
        dest.writeString(typeId);
        dest.writeInt(discount);
        dest.writeParcelable(customer, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Object getMembers() {
        return members;
    }

    public void setMembers(Object members) {
        this.members = members;
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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return entityType + " " + name + " | " + information;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    @Type
    public int getProjectType() {
        return Type.TYPE_NONE;
    }

    public com.example.paralect.easytime.model.Type getStatus() {
        return status;
    }

    public void setStatus(com.example.paralect.easytime.model.Type status) {
        this.status = status;
    }

    public static Job fromBundle(Bundle bundle) {
        if (bundle != null && bundle.containsKey(TAG))
            return bundle.getParcelable(TAG);
        else return null;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
