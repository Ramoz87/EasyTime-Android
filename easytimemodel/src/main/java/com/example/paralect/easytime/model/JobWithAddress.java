package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexei on 04.01.2018.
 */

public class JobWithAddress extends Job implements Parcelable {

    private String addressString;
    private String cityString;
    private String zipString;
    private long addressId;

    private Address address;

    public JobWithAddress() {

    }

    protected JobWithAddress(Parcel in) {
        super(in);
        addressString = in.readString();
        cityString = in.readString();
        zipString = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
        addressId = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(addressString);
        parcel.writeString(cityString);
        parcel.writeString(zipString);
        parcel.writeParcelable(address, i);
        parcel.writeLong(addressId);
    }

    public static final Creator<JobWithAddress> CREATOR = new Creator<JobWithAddress>() {
        @Override
        public JobWithAddress createFromParcel(Parcel in) {
            return new JobWithAddress(in);
        }

        @Override
        public JobWithAddress[] newArray(int size) {
            return new JobWithAddress[size];
        }
    };

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getCityString() {
        return cityString;
    }

    public void setCityString(String cityString) {
        this.cityString = cityString;
    }

    public String getZipString() {
        return zipString;
    }

    public void setZipString(String zipString) {
        this.zipString = zipString;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        if (address != null) return super.toString() + " | " + address.toString();
        else return super.toString() + " | " + cityString + " " + addressString;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
