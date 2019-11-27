package com.example.androidweatherchallenge.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androidweatherchallenge.model.city.CityResult;
import com.example.androidweatherchallenge.model.forecast.ForecastResult;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(CityResult city);

    @Query("DELETE FROM cities where dt NOT IN (SELECT dt from cities ORDER BY dt DESC LIMIT 10)")
    void deleteAllButRecents();

    @Query("SELECT * FROM cities ORDER BY dt DESC LIMIT 10")
    Observable<List<CityResult>> getAllCities();

    @Query("SELECT * FROM cities LIMIT 1 OFFSET (SELECT COUNT(*) FROM cities)-1")
    Observable<CityResult> getLastCity();

    @Query("SELECT * FROM cities WHERE id == :id")
    Observable<CityResult> getCityDetails(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForecast(ForecastResult forecasts);

    @Query("DELETE FROM forecasts where city_id NOT IN (SELECT id from cities ORDER BY dt DESC LIMIT 10)")
    void cleanForecasts();

    @Query("SELECT * FROM forecasts WHERE city_id == :cityId")
    Observable<ForecastResult> getForecastForCity(Long cityId);
}
