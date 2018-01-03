package com.example.paralect.easytime.model;

import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

public class Project extends Job implements Parcelable {

    @DatabaseField(columnName = "dateEnd")
    private String dateEnd;

    @DatabaseField(columnName = "dateStart")
    private String dateStart;

    private List<Object> objects;

    public Project() {

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
}
