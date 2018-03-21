package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.paralect.datasource.core.Model;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "objects")
public class Object extends JobWithAddress implements Parcelable, ProjectType {

    @DatabaseField(columnName = "dateEnd")
    private String dateEnd;

    @DatabaseField(columnName = "dateStart")
    private String dateStart;

    @DatabaseField(columnName = "projectId")
    private String projectId;

    public Object() {

    }

    protected Object(Parcel in) {
        super(in);
        dateEnd = in.readString();
        dateStart = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(dateEnd);
        parcel.writeString(dateStart);
    }

    public static final Creator<Object> CREATOR = new Creator<Object>() {
        @Override
        public Object createFromParcel(Parcel in) {
            return new Object(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[size];
        }
    };

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

    @Override
    public String toString() {
        return super.toString() + " | " + dateStart + " " + dateEnd;
    }

    @Override
    @Type
    public int getProjectType() {
        return Type.TYPE_OBJECT;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getFullAddress(){
        String fullAddress = null;
        Address ad = getAddress();
        if (ad != null) {
            fullAddress = ad.getFullAddress();
        }
        return fullAddress;
    }
}
