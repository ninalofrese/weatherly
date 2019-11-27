
package com.example.androidweatherchallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Clouds implements Parcelable {

    @Expose
    private Long all;

    public Clouds() {
    }

    protected Clouds(Parcel in) {
        if (in.readByte() == 0) {
            all = null;
        } else {
            all = in.readLong();
        }
    }

    public static final Creator<Clouds> CREATOR = new Creator<Clouds>() {
        @Override
        public Clouds createFromParcel(Parcel in) {
            return new Clouds(in);
        }

        @Override
        public Clouds[] newArray(int size) {
            return new Clouds[size];
        }
    };

    public Long getAll() {
        return all;
    }

    public void setAll(Long all) {
        this.all = all;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (all == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(all);
        }
    }
}
