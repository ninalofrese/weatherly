
package com.example.androidweatherchallenge.model.forecast;

import androidx.room.Embedded;
import androidx.room.PrimaryKey;

import com.example.androidweatherchallenge.model.Coord;
import com.google.gson.annotations.Expose;

import io.reactivex.annotations.NonNull;

public class City {

    @Expose
    @Embedded
    private Coord coord;
    @Expose
    private String country;
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private Long sunrise;
    @Expose
    private Long sunset;
    @Expose
    private Long timezone;

    public City() {
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getTimezone() {
        return timezone;
    }

    public void setTimezone(Long timezone) {
        this.timezone = timezone;
    }

}
