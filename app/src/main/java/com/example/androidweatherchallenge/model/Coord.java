
package com.example.androidweatherchallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Coord implements Parcelable {

    @Expose
    private Double lat;
    @Expose
    private Double lon;

    public Coord() {
    }

    protected Coord(Parcel in) {
        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }
        if (in.readByte() == 0) {
            lon = null;
        } else {
            lon = in.readDouble();
        }
    }

    public static final Creator<Coord> CREATOR = new Creator<Coord>() {
        @Override
        public Coord createFromParcel(Parcel in) {
            return new Coord(in);
        }

        @Override
        public Coord[] newArray(int size) {
            return new Coord[size];
        }
    };

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (lat == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(lat);
        }
        if (lon == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(lon);
        }
    }
}
