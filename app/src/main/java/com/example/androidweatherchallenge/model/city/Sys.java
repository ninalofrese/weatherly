
package com.example.androidweatherchallenge.model.city;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Sys implements Parcelable {

    @Expose
    private String country;
    @Expose
    private Long id;
    @Expose
    private Long sunrise;
    @Expose
    private Long sunset;
    @Expose
    private Long type;

    public Sys() {
    }

    protected Sys(Parcel in) {
        country = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            sunrise = null;
        } else {
            sunrise = in.readLong();
        }
        if (in.readByte() == 0) {
            sunset = null;
        } else {
            sunset = in.readLong();
        }
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readLong();
        }
    }

    public static final Creator<Sys> CREATOR = new Creator<Sys>() {
        @Override
        public Sys createFromParcel(Parcel in) {
            return new Sys(in);
        }

        @Override
        public Sys[] newArray(int size) {
            return new Sys[size];
        }
    };

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(country);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        if (sunrise == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(sunrise);
        }
        if (sunset == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(sunset);
        }
        if (type == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(type);
        }
    }
}
