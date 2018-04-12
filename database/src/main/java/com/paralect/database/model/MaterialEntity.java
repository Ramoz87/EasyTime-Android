package com.paralect.database.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.paralect.easytime.model.Constants.IS_ADDED;
import static com.example.paralect.easytime.model.Constants.MATERIAL_ID;
import static com.example.paralect.easytime.model.Constants.NAME;
import static com.example.paralect.easytime.model.Constants.STOCK_ENTITY;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "materials")
public class MaterialEntity {

    @DatabaseField(columnName = "currency")
    private String currency;

    @DatabaseField(columnName = MATERIAL_ID, id = true)
    private String materialId;

    @DatabaseField(columnName = "materialNr")
    private int materialNr;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = "pricePerUnit")
    private int pricePerUnit;

    @DatabaseField(columnName = "serialNr")
    private long serialNr;

    @DatabaseField(columnName = "unitId")
    private String unitId;

    @DatabaseField(columnName = IS_ADDED, dataType = DataType.BOOLEAN)
    private boolean isAdded = false;

    @DatabaseField(columnName = STOCK_ENTITY)
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
