package com.example.paralect.easytime.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.lang.*;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "types")
public class Type {

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "type")
    private String type;

    @DatabaseField(columnName = "typeId", id = true)
    private String typeId;

    public Type() {

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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }

    @Override
    public boolean equals(java.lang.Object object) {
        if (object == null || !(object instanceof Type)) return false;

        return typeId.equals(((Type) object).getTypeId());
    }
}
