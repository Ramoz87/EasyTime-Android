package com.example.paralect.easytime.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.paralect.easytime.utils.CalendarUtils;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;
import java.util.List;

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
    private long date;

    @DatabaseField(columnName = "entityType")
    private String entityType = "job";

    @DatabaseField(columnName = "information")
    private String information;

    @DatabaseField(columnName = "jobId", id = true)
    private String jobId;

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

    @DatabaseField(columnName = "userIds", dataType = DataType.SERIALIZABLE)
    private String[] userIds;

    private File image;
    private Customer customer;
    private com.example.paralect.easytime.model.Type status;
    private List<User> members;

    public Job() {

    }

    protected Job(Parcel in) {
        currency = in.readString();
        customerId = in.readString();
        date = in.readLong();
        entityType = in.readString();
        information = in.readString();
        jobId = in.readString();
        name = in.readString();
        number = in.readInt();
        statusId = in.readString();
        typeId = in.readString();
        discount = in.readInt();
        userIds = in.createStringArray();
        customer = in.readParcelable(Customer.class.getClassLoader());
        status = in.readParcelable(com.example.paralect.easytime.model.Type.class.getClassLoader());
        members = in.createTypedArrayList(User.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currency);
        dest.writeString(customerId);
        dest.writeLong(date);
        dest.writeString(entityType);
        dest.writeString(information);
        dest.writeString(jobId);
        dest.writeString(name);
        dest.writeInt(number);
        dest.writeString(statusId);
        dest.writeString(typeId);
        dest.writeInt(discount);
        dest.writeStringArray(userIds);
        dest.writeParcelable(customer, flags);
        dest.writeParcelable(status, flags);
        dest.writeTypedList(members);
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

    public long getDate() {
        return date;
    }

    public String getStringDate() {
        return CalendarUtils.stringFromDate(new Date(date), CalendarUtils.SHORT_DATE_FORMAT);
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getNumberWithName(){
        return number + ": " + name;
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

    public String[] getMemberIds() {
        return userIds;
    }

    public void setMemberIds(String[] userIds) {
        this.userIds = userIds;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
