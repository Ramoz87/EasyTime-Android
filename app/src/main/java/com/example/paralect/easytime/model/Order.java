package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.paralect.datasource.core.Model;

import java.util.ArrayList;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "orders")
public class Order extends JobWithAddress implements Parcelable, ProjectType, ObjectCollection {

    @DatabaseField(columnName = "contact")
    private String contact;

    @DatabaseField(columnName = "deliveryTime")
    private String deliveryTime;

    @DatabaseField(columnName = "objectIds", dataType = DataType.SERIALIZABLE)
    private String[] objectIds;

    private ArrayList<Object> objects;

    public Order() {

    }

    protected Order(Parcel in) {
        super(in);
        contact = in.readString();
        deliveryTime = in.readString();
        objectIds = in.createStringArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(contact);
        parcel.writeString(deliveryTime);
        parcel.writeStringArray(objectIds);
    }

    public static final Creator<Object> CREATOR = new Creator<Object>() {
        @Override
        public Object createFromParcel(Parcel in) {
            return new Object(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[size];
        }
    };

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public String toString() {
        return super.toString() + " |  " + contact;
    }

    @Override
    @Type
    public int getProjectType() {
        return Type.TYPE_ORDER;
    }

    @Override
    public String[] getObjectIds() {
        return objectIds;
    }

    public void setObjectIds(String[] objectIds) {
        this.objectIds = objectIds;
    }
}
