package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.paralect.easytime.model.Constants.NAME;
import static com.example.paralect.easytime.model.Constants.TYPE_ID;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "types")
public class TypeEntity {

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = "type")
    private String type;

    @DatabaseField(columnName = TYPE_ID, id = true)
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
