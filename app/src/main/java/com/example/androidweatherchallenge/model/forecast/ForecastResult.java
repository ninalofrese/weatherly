
package com.example.androidweatherchallenge.model.forecast;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.androidweatherchallenge.model.city.CityResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.annotations.NonNull;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "forecasts")
public class ForecastResult {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Long forecastId;
    @Expose
    @Embedded(prefix = "city_")
    private City city;
    @Expose
    private Long cnt;
    @Expose
    private String cod;
    @Expose
    @SerializedName("list")
    private List<Forecast> forecast;
    @Expose
    private Long message;

    public ForecastResult() {
    }

    public Long getForecastId() {
        return forecastId;
    }

    public void setForecastId(Long forecastId) {
        this.forecastId = forecastId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public java.util.List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(java.util.List<Forecast> forecast) {
        this.forecast = forecast;
    }

    public Long getMessage() {
        return message;
    }

    public void setMessage(Long message) {
        this.message = message;
    }

}
