package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

public class Job implements Parcelable {
    private String currency;
    private long customerId;
    private String date;
    private String entityType;
    private String information;
    private long jobId;
    private Object members;
    private String name;
    private int number;
    private int statusId;
    private int typeId;

    private List<Expense> expenses;
    private File image;

    public Job() {

    }

    protected Job(Parcel in) {
        currency = in.readString();
        customerId = in.readLong();
        date = in.readString();
        entityType = in.readString();
        information = in.readString();
        jobId = in.readLong();
        members = in.readParcelable(Object.class.getClassLoader());
        name = in.readString();
        number = in.readInt();
        statusId = in.readInt();
        typeId = in.readInt();
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

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(currency);
        parcel.writeLong(customerId);
        parcel.writeString(date);
        parcel.writeString(entityType);
        parcel.writeString(information);
        parcel.writeLong(jobId);
        parcel.writeParcelable(members, i);
        parcel.writeString(name);
        parcel.writeInt(number);
        parcel.writeInt(statusId);
        parcel.writeInt(typeId);
    }
}
