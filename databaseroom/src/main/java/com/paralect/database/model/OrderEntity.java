package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import com.paralect.database.converter.StringArrayConverter;

import static com.example.paralect.easytime.model.Constants.OBJECT_IDS;

/**
 * Created by alexei on 26.12.2017.
 */

@Entity(tableName = "orders")
public class OrderEntity extends JobWithAddressEntity {

    @ColumnInfo(name = "contact")
    private String contact;

    @ColumnInfo(name = "deliveryTime")
    private String deliveryTime;

    @TypeConverters(StringArrayConverter.class)
    @ColumnInfo(name = OBJECT_IDS)
    private String[] objectIds;

    public OrderEntity() {

    }

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

    public String[] getObjectIds() {
        return objectIds;
    }

    public void setObjectIds(String[] objectIds) {
        this.objectIds = objectIds;
    }
}
