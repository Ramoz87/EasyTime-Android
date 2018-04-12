package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import static com.example.paralect.easytime.model.Constants.PROJECT_ID;

/**
 * Created by alexei on 26.12.2017.
 */

@Entity(tableName = "objects")
public class ObjectEntity extends JobWithAddressEntity {

    @ColumnInfo(name = "dateEnd")
    private String dateEnd;

    @ColumnInfo(name = "dateStart")
    private String dateStart;

    @ColumnInfo(name = PROJECT_ID)
    private String projectId;

    public ObjectEntity() {

    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

}
