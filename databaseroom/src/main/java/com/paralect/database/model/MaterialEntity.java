package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static com.example.paralect.easytime.model.Constants.IS_ADDED;
import static com.example.paralect.easytime.model.Constants.MATERIAL_ID;
import static com.example.paralect.easytime.model.Constants.NAME;
import static com.example.paralect.easytime.model.Constants.STOCK_ENTITY;

/**
 * Created by alexei on 26.12.2017.
 */

@Entity(tableName = "materials")
public class MaterialEntity {

    @ColumnInfo(name = "currency")
    private String currency;

    @PrimaryKey
    @ColumnInfo(name = MATERIAL_ID)
    private String materialId;

    @ColumnInfo(name = "materialNr")
    private int materialNr;

    @ColumnInfo(name = NAME)
    private String name;

    @ColumnInfo(name = "pricePerUnit")
    private int pricePerUnit;

    @ColumnInfo(name = "serialNr")
    private long serialNr;

    @ColumnInfo(name = "unitId")
    private String unitId;

    @ColumnInfo(name = IS_ADDED)
    private boolean isAdded = false;

    @ColumnInfo(name = STOCK_ENTITY)
    private int stockQuantity = 0;

    public MaterialEntity() {

    }

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

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
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

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return name;
    }

}
