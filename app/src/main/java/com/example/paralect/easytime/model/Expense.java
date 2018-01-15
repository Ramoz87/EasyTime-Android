package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexei on 26.12.2017.
 */

public class Expense implements Parcelable {

    private float discount;
    private int expensiveId;
    private String materialId;
    private String name;
    private String type;
    private int value;
    private String workTypeId;

    private Job job;
    private File photo;

    public Expense() {

    }

    protected Expense(Parcel in) {
        discount = in.readFloat();
        expensiveId = in.readInt();
        materialId = in.readString();
        name = in.readString();
        type = in.readString();
        value = in.readInt();
        workTypeId = in.readString();
        job = in.readParcelable(Job.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(discount);
        dest.writeInt(expensiveId);
        dest.writeString(materialId);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(value);
        dest.writeString(workTypeId);
        dest.writeParcelable(job, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getExpensiveId() {
        return expensiveId;
    }

    public void setExpensiveId(int expensiveId) {
        this.expensiveId = expensiveId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(String workTypeId) {
        this.workTypeId = workTypeId;
    }
}
