
package com.example.androidweatherchallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main implements Parcelable {

    @SerializedName("grnd_level")
    private Long grndLevel;
    @Expose
    private Long humidity;
    @Expose
    private Long pressure;
    @SerializedName("sea_level")
    private Long seaLevel;
    @Expose
    private Double temp;
    @SerializedName("temp_kf")
    private Double tempKf;
    @SerializedName("temp_max")
    private Double tempMax;
    @SerializedName("temp_min")
    private Double tempMin;

    public Main() {
    }

    protected Main(Parcel in) {
        if (in.readByte() == 0) {
            grndLevel = null;
        } else {
            grndLevel = in.readLong();
        }
        if (in.readByte() == 0) {
            humidity = null;
        } else {
            humidity = in.readLong();
        }
        if (in.readByte() == 0) {
            pressure = null;
        } else {
            pressure = in.readLong();
        }
        if (in.readByte() == 0) {
            seaLevel = null;
        } else {
            seaLevel = in.readLong();
        }
        if (in.readByte() == 0) {
            temp = null;
        } else {
            temp = in.readDouble();
        }
        if (in.readByte() == 0) {
            tempKf = null;
        } else {
            tempKf = in.readDouble();
        }
        if (in.readByte() == 0) {
            tempMax = null;
        } else {
            tempMax = in.readDouble();
        }
        if (in.readByte() == 0) {
            tempMin = null;
        } else {
            tempMin = in.readDouble();
        }
    }

    public static final Creator<Main> CREATOR = new Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel in) {
            return new Main(in);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[size];
        }
    };

    public Long getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Long grndLevel) {
        this.grndLevel = grndLevel;
    }

    public Long getHumidity() {
        return humidity;
    }

    public void setHumidity(Long humidity) {
        this.humidity = humidity;
    }

    public Long getPressure() {
        return pressure;
    }

    public void setPressure(Long pressure) {
        this.pressure = pressure;
    }

    public Long getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Long seaLevel) {
        this.seaLevel = seaLevel;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTempKf() {
        return tempKf;
    }

    public void setTempKf(Double tempKf) {
        this.tempKf = tempKf;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (grndLevel == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(grndLevel);
        }
        if (humidity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(humidity);
        }
        if (pressure == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(pressure);
        }
        if (seaLevel == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(seaLevel);
        }
        if (temp == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(temp);
        }
        if (tempKf == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(tempKf);
        }
        if (tempMax == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(tempMax);
        }
        if (tempMin == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(tempMin);
        }
    }
}
