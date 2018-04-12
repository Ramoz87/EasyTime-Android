package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static com.example.paralect.easytime.model.Constants.NAME;
import static com.example.paralect.easytime.model.Constants.TYPE_ID;

/**
 * Created by alexei on 26.12.2017.
 */

@Entity(tableName = "types")
public class TypeEntity {

    @ColumnInfo(name = NAME)
    private String name;

    @ColumnInfo(name = "type")
    private String type;

    @PrimaryKey
    @ColumnInfo(name = TYPE_ID)
    private String typeId;

    public TypeEntity() {

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

}
