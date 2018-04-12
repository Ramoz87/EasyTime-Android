package com.paralect.database.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.paralect.easytime.model.Constants.OBJECT_IDS;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "orders")
public class OrderEntity extends JobWithAddressEntity {

    @DatabaseField(columnName = "contact")
    private String contact;

    @DatabaseField(columnName = "deliveryTime")
    private String deliveryTime;

    @DatabaseField(columnName = OBJECT_IDS, dataType = DataType.SERIALIZABLE)
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
