
package com.example.androidweatherchallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Wind implements Parcelable {

    @Expose
    private Long deg;
    @Expose
    private Double speed;

    public Wind() {
    }

    protected Wind(Parcel in) {
        if (in.readByte() == 0) {
            deg = null;
        } else {
            deg = in.readLong();
        }
        if (in.readByte() == 0) {
            speed = null;
        } else {
            speed = in.readDouble();
        }
    }

    public static final Creator<Wind> CREATOR = new Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel in) {
            return new Wind(in);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };

    public Long getDeg() {
        return deg;
    }

    public void setDeg(Long deg) {
        this.deg = deg;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (deg == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(deg);
        }
        if (speed == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(speed);
        }
    }
}
