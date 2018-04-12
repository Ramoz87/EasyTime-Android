package com.paralect.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.paralect.easytime.model.Constants.PROJECT_ID;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "objects")
public class ObjectEntity extends JobWithAddressEntity {

    @DatabaseField(columnName = "dateEnd")
    private String dateEnd;

    @DatabaseField(columnName = "dateStart")
    private String dateStart;

    @DatabaseField(columnName = PROJECT_ID)
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
