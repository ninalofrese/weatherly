package com.example.androidweatherchallenge.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidweatherchallenge.model.city.CityResult;
import com.example.androidweatherchallenge.repository.WeatherRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RecentCitiesActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<CityResult>> cities = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private WeatherRepository repository = new WeatherRepository();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public RecentCitiesActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CityResult>> getCities() {
        return this.cities;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public LiveData<String> getError() {
        return this.error;
    }

    public void getLocalCities() {
        disposable.add(
                repository.getLocalCities(getApplication().getApplicationContext())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> {
                            loading.setValue(true);
                        })
                        .doAfterTerminate(() -> {
                            loading.setValue(false);
                        })
                        .subscribe(cityResults -> {
                            cities.setValue(cityResults);
                            loading.setValue(false);
                        }, throwable -> {
                            error.setValue(throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
