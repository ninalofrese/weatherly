package com.example.androidweatherchallenge.repository;

import android.content.Context;

import com.example.androidweatherchallenge.data.local.CityDao;
import com.example.androidweatherchallenge.data.local.Database;
import com.example.androidweatherchallenge.model.city.CityResult;
import com.example.androidweatherchallenge.model.forecast.ForecastResult;

import java.util.List;

import io.reactivex.Observable;

import static com.example.androidweatherchallenge.data.network.RetrofitService.getApiService;

public class WeatherRepository {

    public Observable<CityResult> getCity(String apiKey, String cityName, String units) {
        return getApiService().getCity(apiKey, cityName, units);
    }

    public Observable<ForecastResult> getForecast(String apiKey, Long cityId, String units) {
        return getApiService().getForecast(apiKey, cityId, units);
    }

    public Observable<CityResult> getCityByLocation(String apiKey, Double latitude, Double longitude, String units) {
        return getApiService().getCityByLocation(apiKey, latitude, longitude, units);
    }

    public Observable<List<CityResult>> getLocalCities(Context context) {
        Database room = Database.getDatabase(context);
        CityDao cityDao = room.cityDao();

        return cityDao.getAllCities();
    }

    public Observable<CityResult> getLocalCity(Context context, Long cityId) {
        Database room = Database.getDatabase(context);
        CityDao cityDao = room.cityDao();

        return cityDao.getCityDetails(cityId);
    }

    public Observable<CityResult> getLastCity(Context context) {
        Database room = Database.getDatabase(context);
        CityDao cityDao = room.cityDao();

        return cityDao.getLastCity();
    }

    public void insertCityOnLocal(Context context, CityResult city) {
        Database room = Database.getDatabase(context);
        CityDao cityDao = room.cityDao();
        cityDao.insertCity(city);
        cityDao.deleteAllButRecents();
    }

    public Observable<ForecastResult> getLocalForecasts(Context context, Long cityId) {
        Database room = Database.getDatabase(context);
        CityDao cityDao = room.cityDao();

        return cityDao.getForecastForCity(cityId);
    }

    public void insertForecastsonLocal(Context context, ForecastResult forecast) {
        Database room = Database.getDatabase(context);
        CityDao cityDao = room.cityDao();
        cityDao.insertForecast(forecast);
        cityDao.cleanForecasts();
    }

}
