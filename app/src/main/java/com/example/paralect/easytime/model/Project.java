package com.example.paralect.easytime.model;

import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

public class Project extends Job {

    private String dateEnd;
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
