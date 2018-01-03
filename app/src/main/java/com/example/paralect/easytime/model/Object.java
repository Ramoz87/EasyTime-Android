package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexei on 26.12.2017.
 */

public class Object extends Job implements Parcelable {

    private String dateEnd;
    private String dateStart;
    private Address address;

    public Object() {

    }

    protected Object(Parcel in) {
        super(in);
        dateEnd = in.readString();
        dateStart = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
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
        parcel.writeParcelable(address, i);
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
}
