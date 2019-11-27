package com.example.androidweatherchallenge.data.network;

import com.example.androidweatherchallenge.model.city.CityResult;
import com.example.androidweatherchallenge.model.forecast.ForecastResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather")
    Observable<CityResult> getCity(@Query("appid") String apiKey,
                                   @Query("q") String cityName,
                                   @Query("units") String units);

    @GET("forecast")
    Observable<ForecastResult> getForecast(@Query("appid") String apiKey,
                                           @Query("id") Long cityId,
                                           @Query("units") String units);

    @GET("weather")
    Observable<CityResult> getCityByLocation(@Query("appid") String apiKey,
                                             @Query("lat") Double latitude,
                                             @Query("lon") Double longitude,
                                             @Query("units") String units);

}
