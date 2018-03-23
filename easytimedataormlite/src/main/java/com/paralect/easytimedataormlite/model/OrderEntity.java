package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "orders")
public class OrderEntity extends JobWithAddressEntity {

    @DatabaseField(columnName = "contact")
    private String contact;

    @DatabaseField(columnName = "deliveryTime")
    private String deliveryTime;

    @DatabaseField(columnName = "objectIds", dataType = DataType.SERIALIZABLE)
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
