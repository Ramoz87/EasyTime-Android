package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import com.paralect.database.converter.StringArrayConverter;

import static com.example.paralect.easytime.model.Constants.OBJECT_IDS;

/**
 * Created by alexei on 26.12.2017.
 */

@Entity(tableName = "projects")
public class ProjectEntity extends JobEntity {

    @ColumnInfo(name = "dateStart")
    private String dateStart;

    @ColumnInfo(name = "dateEnd")
    private String dateEnd;

    @TypeConverters(StringArrayConverter.class)
    @ColumnInfo(name = OBJECT_IDS)
    private String[] objectIds;

    public ProjectEntity() {

    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String[] getObjectIds() {
        return objectIds;
    }

    public void setObjectIds(String[] objectIds) {
        this.objectIds = objectIds;
    }
}
