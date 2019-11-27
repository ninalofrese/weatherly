package com.example.androidweatherchallenge.data.local;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.androidweatherchallenge.model.city.CityResult;
import com.example.androidweatherchallenge.model.forecast.ForecastResult;

@androidx.room.Database(entities = {CityResult.class, ForecastResult.class}, version = 8, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class Database extends RoomDatabase {

    private static volatile Database INSTANCE;

    public abstract CityDao cityDao();

    public static Database getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, Database.class, "weather_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
