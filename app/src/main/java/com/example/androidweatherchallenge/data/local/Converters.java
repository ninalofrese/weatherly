package com.example.androidweatherchallenge.data.local;

import androidx.room.TypeConverter;

import com.example.androidweatherchallenge.model.Weather;
import com.example.androidweatherchallenge.model.forecast.Forecast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<Weather> toWeatherList(String value) {
        Type listType = new TypeToken<List<Weather>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromWeatherList(List<Weather> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<Forecast> toForecastList(String value) {
        Type listType = new TypeToken<List<Forecast>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromForecastList(List<Forecast> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
