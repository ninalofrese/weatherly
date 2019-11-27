package com.example.androidweatherchallenge.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.preference.PreferenceManager;

import com.example.androidweatherchallenge.data.local.CityDao;
import com.example.androidweatherchallenge.data.local.Database;
import com.example.androidweatherchallenge.model.city.CityResult;
import com.example.androidweatherchallenge.model.forecast.ForecastResult;
import com.example.androidweatherchallenge.repository.WeatherRepository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.androidweatherchallenge.utils.AppConstants.OWM_API_KEY;
import static com.example.androidweatherchallenge.utils.ConnectionUtil.isNetworkConnected;

public class CityActivityViewModel extends AndroidViewModel {
    private MutableLiveData<CityResult> cityResult = new MutableLiveData<>();
    private LiveData<CityResult> cityFiltered;
    private MutableLiveData<ForecastResult> forecastResult = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private WeatherRepository repository = new WeatherRepository();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<String> unit = new MutableLiveData<>();

    private SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());

    public CityActivityViewModel(@NonNull Application application) {
        super(application);
        unit.setValue(settings.getString("temperature", ""));
    }

    public LiveData<CityResult> getCityResult() {
        return this.cityResult;
    }

    public LiveData<ForecastResult> getForecastResult() {
        return this.forecastResult;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public LiveData<String> getError() {
        return this.error;
    }

    public LiveData<String> getUnit() {
        return this.unit;
    }

    public void getCity(Double latitude, Double longitude, Long cityId) {
        if (isNetworkConnected(getApplication())) {
            getCityByLocation(latitude, longitude);
        } else {
            getLocalCity(cityId);
        }
    }

    public void getLocalCity(Long cityId) {
        disposable.add(
                repository.getLocalCity(getApplication().getApplicationContext(), cityId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> {
                            loading.setValue(true);
                        })
                        .doAfterTerminate(() -> {
                            loading.setValue(false);
                        })
                        .doOnError(throwable -> {
                            error.setValue(throwable.getMessage());
                        })
                        .doOnNext(cityResult1 -> {
                            cityResult.setValue(cityResult1);
                        })
                        .observeOn(Schedulers.newThread())
                        .flatMap(cityResult1 -> {
                            if (cityResult1 != null) {
                                return repository.getLocalForecasts(getApplication().getApplicationContext(), cityResult1.getId());
                            } else {
                                return Observable.empty();
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(throwable -> {
                            error.setValue(throwable.getMessage());
                        })
                        .doOnNext(forecastResult1 -> {
                            forecastResult.setValue(forecastResult1);
                        })
                        .subscribe()
        );
    }

    public void getLastCity() {
        disposable.add(
                repository.getLastCity(getApplication().getApplicationContext())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> {
                            loading.setValue(true);
                        })
                        .doAfterTerminate(() -> {
                            loading.setValue(false);
                        })
                        .doOnError(throwable -> {
                            error.setValue(throwable.getMessage());
                        })
                        .doOnNext(cityResult1 -> {
                            cityResult.setValue(cityResult1);
                        })
                        .observeOn(Schedulers.newThread())
                        .flatMap(cityResult1 -> {
                            if (cityResult1 != null) {
                                return repository.getLocalForecasts(getApplication().getApplicationContext(), cityResult1.getId());
                            } else {
                                return Observable.empty();
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(throwable -> {
                            error.setValue(throwable.getMessage());
                        })
                        .doOnNext(forecastResult1 -> {
                            forecastResult.setValue(forecastResult1);
                        })
                        .subscribe()
        );
    }

    public void getCityByLocation(Double latitude, Double longitude) {
        disposable.add(
                repository.getCityByLocation(OWM_API_KEY, latitude, longitude, settings.getString("temperature", ""))
                        .subscribeOn(Schedulers.io())
                        .map(this::saveCityOnDatabase)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> {
                            loading.setValue(true);
                        })
                        .doAfterTerminate(() -> {
                            loading.setValue(false);
                        })
                        .doOnError(throwable -> {
                            error.setValue(throwable.getMessage());
                        })
                        .doOnNext(cityResult1 -> {
                            cityResult.setValue(cityResult1);
                        })
                        .observeOn(Schedulers.newThread())
                        .flatMap(cityResult1 -> {
                            if (cityResult1 != null) {
                                return repository.getForecast(OWM_API_KEY, cityResult1.getId(), settings.getString("temperature", ""));
                            } else {
                                return Observable.empty();
                            }
                        })
                        .map(this::saveForecastOnDatabase)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(throwable -> {
                            error.setValue(throwable.getMessage());
                        })
                        .doOnNext(forecastResult1 -> {
                            forecastResult.setValue(forecastResult1);
                        })
                        .subscribe()
        );
    }

    private CityResult saveCityOnDatabase(CityResult cityResult) {
        repository.insertCityOnLocal(getApplication().getApplicationContext(), cityResult);

        return cityResult;
    }

    private ForecastResult saveForecastOnDatabase(ForecastResult forecastResult) {
        repository.insertForecastsonLocal(getApplication().getApplicationContext(), forecastResult);

        return forecastResult;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
