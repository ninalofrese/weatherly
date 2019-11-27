package com.example.androidweatherchallenge.data.network;

import com.example.androidweatherchallenge.model.city.CityResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AutoCompleteAPI {

    @GET("json")
    Observable<CityResult> getPrediction(@Query("key") String apiKey,
                                   @Query("types") String types,
                                   @Query("input") String query,
                                   @Query("sensor") Boolean sensor);

}
