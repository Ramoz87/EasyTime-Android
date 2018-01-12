package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "materials")
public class Material implements Parcelable {

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

    @DatabaseField(columnName = "stockQuantity")
    private int stockQuantity;

    @DatabaseField(columnName = "unitId")
    private String unitId;

    public Material() {

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

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
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
}
