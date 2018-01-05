package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.j256.ormlite.dao.BaseForeignCollection;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.EagerForeignCollection;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.lang.*;
import java.lang.Object;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alexei on 26.12.2017.
 */

public class Project extends Job implements Parcelable {

    @DatabaseField(columnName = "dateStart")
    private String dateStart;

    @DatabaseField(columnName = "dateEnd")
    private String dateEnd;

    // @ForeignCollectionField(columnName = "objectIds")
    private List<String> objectIds;

    public Project() {

    }

    protected Project(Parcel in) {
        super(in);
        dateStart = in.readString();
        dateEnd = in.readString();
        objectIds = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(dateStart);
        dest.writeString(dateEnd);
        dest.writeStringList(objectIds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
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

    public List<String> getObjectIds() {
        return objectIds;
    }

    public void setObjectIds(List<String> objectIds) {
        this.objectIds = objectIds;
    }
}
