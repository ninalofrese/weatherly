
package com.example.androidweatherchallenge.model.city;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.androidweatherchallenge.model.Clouds;
import com.example.androidweatherchallenge.model.Coord;
import com.example.androidweatherchallenge.model.Main;
import com.example.androidweatherchallenge.model.Weather;
import com.example.androidweatherchallenge.model.Wind;
import com.google.gson.annotations.Expose;

import java.util.List;

@Entity(tableName = "cities")
public class CityResult implements Parcelable {

    @Expose
    private String base;
    @Expose
    @Embedded
    private Clouds clouds;
    @Expose
    private Long cod;
    @Expose
    @Embedded(prefix = "coord_")
    private Coord coord;
    @Expose
    private Long dt;
    @Expose
    @PrimaryKey
    private Long id;
    @Expose
    @Embedded
    private Main main;
    @Expose
    private String name;
    @Expose
    @Embedded(prefix = "sys_")
    private Sys sys;
    @Expose
    private Long timezone;
    @Expose
    private Long visibility;
    @Expose
    private List<Weather> weather;
    @Expose
    @Embedded
    private Wind wind;

    public CityResult() {
    }

    protected CityResult(Parcel in) {
        base = in.readString();
        clouds = in.readParcelable(Clouds.class.getClassLoader());
        if (in.readByte() == 0) {
            cod = null;
        } else {
            cod = in.readLong();
        }
        coord = in.readParcelable(Coord.class.getClassLoader());
        if (in.readByte() == 0) {
            dt = null;
        } else {
            dt = in.readLong();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        main = in.readParcelable(Main.class.getClassLoader());
        name = in.readString();
        sys = in.readParcelable(Sys.class.getClassLoader());
        if (in.readByte() == 0) {
            timezone = null;
        } else {
            timezone = in.readLong();
        }
        if (in.readByte() == 0) {
            visibility = null;
        } else {
            visibility = in.readLong();
        }
        weather = in.createTypedArrayList(Weather.CREATOR);
        wind = in.readParcelable(Wind.class.getClassLoader());
    }

    public static final Creator<CityResult> CREATOR = new Creator<CityResult>() {
        @Override
        public CityResult createFromParcel(Parcel in) {
            return new CityResult(in);
        }

        @Override
        public CityResult[] newArray(int size) {
            return new CityResult[size];
        }
    };

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Long getTimezone() {
        return timezone;
    }

    public void setTimezone(Long timezone) {
        this.timezone = timezone;
    }

    public Long getVisibility() {
        return visibility;
    }

    public void setVisibility(Long visibility) {
        this.visibility = visibility;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(base);
        parcel.writeParcelable(clouds, i);
        if (cod == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(cod);
        }
        parcel.writeParcelable(coord, i);
        if (dt == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(dt);
        }
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeParcelable(main, i);
        parcel.writeString(name);
        parcel.writeParcelable(sys, i);
        if (timezone == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(timezone);
        }
        if (visibility == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(visibility);
        }
        parcel.writeTypedList(weather);
        parcel.writeParcelable(wind, i);
    }
}
