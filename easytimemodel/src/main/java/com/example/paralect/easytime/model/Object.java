package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexei on 26.12.2017.
 */

public class Object extends JobWithAddress implements Parcelable, ProjectType {

    private String dateEnd;
    private String dateStart;
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
