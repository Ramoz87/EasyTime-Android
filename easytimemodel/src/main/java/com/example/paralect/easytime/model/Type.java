package com.example.paralect.easytime.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by alexei on 26.12.2017.
 */

public class Type implements Parcelable{

    public static final String TAG = Type.class.getSimpleName();

    @StringDef({TypeName.WORK_TYPE, TypeName.STATUS, TypeName.UNIT_TYPE, TypeName.JOB_TYPE, TypeName.EXPENSE_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TypeName {
        String WORK_TYPE = "WORK_TYPE";
        String STATUS = "STATUS";
        String UNIT_TYPE = "UNIT_TYPE";
        String JOB_TYPE = "JOB_TYPE";
        String EXPENSE_TYPE = "EXPENSE_TYPE";
    }

    private String name;
    private String type;
    private String typeId;

    public Type() {

    }

    // region Parcelable
    protected Type(Parcel in) {
        name = in.readString();
        type = in.readString();
        typeId = in.readString();
    }

    public static final Creator<Type> CREATOR = new Creator<Type>() {
        @Override
        public Type createFromParcel(Parcel in) {
            return new Type(in);
        }

        @Override
        public Type[] newArray(int size) {
            return new Type[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(typeId);
    }
    // endregion

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

    @Override
    public String toString() {
        return type + " " + name;
    }

    @Override
    public boolean equals(java.lang.Object object) {
        if (object == null || !(object instanceof Type)) return false;

        return typeId.equals(((Type) object).getTypeId());
    }

    public static Type getType(Bundle bundle) {
        if (bundle != null && bundle.containsKey(TAG))
            return bundle.getParcelable(TAG);
        else return null;
    }
}
