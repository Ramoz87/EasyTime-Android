package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.paralect.core.Model;

import java.lang.*;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "materials")
public class Material implements Parcelable, Model<String> {

    @DatabaseField(columnName = "currency")
    private String currency;

    @DatabaseField(columnName = "materialId", id = true)
    private String materialId;

    @DatabaseField(columnName = "materialNr")
    private int materialNr;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "pricePerUnit")
    private int pricePerUnit;

    @DatabaseField(columnName = "serialNr")
    private long serialNr;

    @DatabaseField(columnName = "unitId")
    private String unitId;

    @DatabaseField(columnName = "isAdded", dataType = DataType.BOOLEAN)
    private boolean isAdded = false;

    @DatabaseField(columnName = "stockQuantity")
    private int stockQuantity = 0;

    public Material() {

    }

    public Material(Material toCopy) {
        this.materialId = toCopy.materialId;
        this.currency = toCopy.currency;
        this.materialNr = toCopy.materialNr;
        this.name = toCopy.name;
        this.pricePerUnit = toCopy.pricePerUnit;
        this.serialNr = toCopy.serialNr;
        this.stockQuantity = toCopy.stockQuantity;
        this.unitId = toCopy.unitId;
    }

    protected Material(Parcel in) {
        currency = in.readString();
        materialId = in.readString();
        materialNr = in.readInt();
        name = in.readString();
        pricePerUnit = in.readInt();
        serialNr = in.readLong();
        stockQuantity = in.readInt();
        unitId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currency);
        dest.writeString(materialId);
        dest.writeInt(materialNr);
        dest.writeString(name);
        dest.writeInt(pricePerUnit);
        dest.writeLong(serialNr);
        dest.writeInt(stockQuantity);
        dest.writeString(unitId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Material> CREATOR = new Creator<Material>() {
        @Override
        public Material createFromParcel(Parcel in) {
            return new Material(in);
        }

        @Override
        public Material[] newArray(int size) {
            return new Material[size];
        }
    };

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getId() {
        return materialId;
    }

    public void setId(String materialId) {
        this.materialId = materialId;
    }

    public int getMaterialNr() {
        return materialNr;
    }

    public void setMaterialNr(int materialNr) {
        this.materialNr = materialNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(int pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public long getSerialNr() {
        return serialNr;
    }

    public void setSerialNr(long serialNr) {
        this.serialNr = serialNr;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int count) {
        this.stockQuantity = count;
    }

    @Override
    public boolean equals(java.lang.Object object) {
        if (object == null || !(object instanceof Material)) return false;

        return materialId.equals(((Material) object).getId());
    }

}
