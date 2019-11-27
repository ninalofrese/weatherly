
package com.example.androidweatherchallenge.model.forecast;

import com.example.androidweatherchallenge.model.Main;
import com.example.androidweatherchallenge.model.Weather;
import com.example.androidweatherchallenge.model.Clouds;
import com.example.androidweatherchallenge.model.Wind;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Forecast {

    @Expose
    private Clouds clouds;
    @Expose
    private Long dt;
    @SerializedName("dt_txt")
    private String dtTxt;
    @Expose
    private Main main;
    @Expose
    private Sys sys;
    @Expose
    private List<Weather> weather = new ArrayList<>();
    @Expose
    private Wind wind;

    public Forecast() {
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

}
