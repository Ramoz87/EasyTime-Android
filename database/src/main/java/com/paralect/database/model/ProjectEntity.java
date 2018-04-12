package com.paralect.database.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.paralect.easytime.model.Constants.OBJECT_IDS;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "projects")
public class ProjectEntity extends JobEntity {

    @DatabaseField(columnName = "dateStart")
    private String dateStart;

    @DatabaseField(columnName = "dateEnd")
    private String dateEnd;

    // @ForeignCollectionField(columnName = "objectIds")
    @DatabaseField(columnName = OBJECT_IDS, dataType = DataType.SERIALIZABLE)
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
