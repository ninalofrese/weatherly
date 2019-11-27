package com.example.androidweatherchallenge.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.androidweatherchallenge.R;
import com.example.androidweatherchallenge.model.city.CityResult;
import com.example.androidweatherchallenge.model.forecast.Forecast;
import com.example.androidweatherchallenge.view.adapters.RecyclerForecastAdapter;
import com.example.androidweatherchallenge.viewmodel.CityActivityViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.androidweatherchallenge.utils.AppConstants.LOCATION_REQUEST_CODE;
import static com.example.androidweatherchallenge.utils.ConnectionUtil.isNetworkConnected;
import static com.example.androidweatherchallenge.utils.ConvertersUtils.convertTime;
import static com.example.androidweatherchallenge.utils.ConvertersUtils.convertToKmh;
import static com.example.androidweatherchallenge.utils.ConvertersUtils.convertToMph;
import static com.example.androidweatherchallenge.utils.ConvertersUtils.convertWindDirection;
import static com.example.androidweatherchallenge.view.MainActivity.CITY_KEY;
import static com.example.androidweatherchallenge.view.MainActivity.LAT_KEY;
import static com.example.androidweatherchallenge.view.MainActivity.LON_KEY;

public class CityActivity extends AppCompatActivity {
    private static final int SETTINGS_CODE = 100;
    public static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private ImageView cityIcon;
    private TextView cityTemperature;
    private TextView cityUnit;
    private TextView cityWeatherText;
    private TextView cityName;
    private TextView cityTempMax;
    private TextView cityTempMin;
    private TextView citySunrise;
    private TextView citySunset;
    private TextView cityWindDirection;
    private TextView cityWindSpeed;
    private RecyclerView recyclerForecast;
    private RecyclerForecastAdapter adapter;
    private List<Forecast> forecastList = new ArrayList<>();
    private CityActivityViewModel viewModel;
    private ProgressBar progressBar;
    private RelativeLayout pageContent;
    private SwipeRefreshLayout swipeLayout;

    private FusedLocationProviderClient fusedLocationClient;
    SharedPreferences settings;
    private Handler handler = new Handler();
    private CityResult currentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initViews();

        displayError();
        displayLoading();

        if (getIntent() != null && getIntent().getExtras() != null) {
            currentCity = getIntent().getParcelableExtra(CITY_KEY);
            viewModel.getCity(currentCity.getCoord().getLat(), currentCity.getCoord().getLon(), currentCity.getId());
        } else {
            getCityData();
        }

        getCurrentWeather();
        getForecast();
    }

    private void initViews() {
        pageContent = findViewById(R.id.weather_content);
        cityIcon = findViewById(R.id.icon_city_weather);
        cityTemperature = findViewById(R.id.text_city_temperature);
        cityUnit = findViewById(R.id.text_city_unit);
        cityWeatherText = findViewById(R.id.text_city_text);
        cityName = findViewById(R.id.text_city_name);
        cityTempMax = findViewById(R.id.text_city_tempmax);
        cityTempMin = findViewById(R.id.text_city_tempmin);
        citySunrise = findViewById(R.id.text_city_sunrise);
        citySunset = findViewById(R.id.text_city_sunset);
        cityWindDirection = findViewById(R.id.text_city_winddirection);
        cityWindSpeed = findViewById(R.id.text_city_windspeed);
        recyclerForecast = findViewById(R.id.recycler_city_forecast);
        progressBar = findViewById(R.id.progress_bar);
        viewModel = ViewModelProviders.of(this).get(CityActivityViewModel.class);
        adapter = new RecyclerForecastAdapter(forecastList);
        recyclerForecast.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerForecast.addItemDecoration(new DividerItemDecoration(recyclerForecast.getContext(),
                DividerItemDecoration.HORIZONTAL));
        recyclerForecast.setAdapter(adapter);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(() -> {
            handler.postDelayed(() -> {

                if (swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                }
            }, 1000);
            reloadPage();
        });

    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        currentCity = savedInstanceState.getParcelable(CITY_KEY);
//        viewModel.getCity(currentCity.getCoord().getLat(), currentCity.getCoord().getLon(), currentCity.getId());
//        super.onRestoreInstanceState(savedInstanceState);
//    }
//
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putParcelable(CITY_KEY, currentCity);
//        super.onSaveInstanceState(outState);
//    }

    public void getCityData() {
        if (isNetworkConnected(getApplication())) {
            getLocationData();
        } else {
            viewModel.getLastCity();
        }
    }

    private void reloadPage() {
        if (currentCity != null) {
            viewModel.getCityByLocation(currentCity.getCoord().getLat(), currentCity.getCoord().getLon());
        } else {
            getLocationData();
        }
    }

    private void getLocationData() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            viewModel.getCityByLocation(location.getLatitude(), location.getLongitude());
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }

    }

    private void getCurrentWeather() {
        viewModel.getCityResult().observe(CityActivity.this, cityResult -> {
            if (cityResult != null) {
                currentCity = cityResult;
                String iconUri = "http://openweathermap.org/img/wn/" + cityResult.getWeather().get(0).getIcon() + "@2x.png";
                Picasso.get().load(iconUri).into(cityIcon);
                cityTemperature.setText(String.format(Locale.US, "%.0f", cityResult.getMain().getTemp()));
                if (settings.getString("temperature", "metric").equals("metric")) {
                    cityUnit.setText(R.string.metric);
                } else {
                    cityUnit.setText(R.string.imperial);
                }
                cityWeatherText.setText(cityResult.getWeather().get(0).getMain());
                cityName.setText(cityResult.getName());
                cityTempMax.setText(String.format(Locale.US, "%.0f\u00B0", cityResult.getMain().getTempMax()));
                cityTempMin.setText(String.format(Locale.US, "%.0f\u00B0", cityResult.getMain().getTempMin()));
                citySunrise.setText(convertTime(cityResult.getSys().getSunrise()));
                citySunset.setText(convertTime(cityResult.getSys().getSunset()));
                if (cityResult.getWind().getDeg() != null) {
                    cityWindDirection.setText(convertWindDirection(cityResult.getWind().getDeg()));
                } else {
                    cityWindDirection.setText("N/D");
                }
                if (settings.getString("wind", "mph").equals("kmh")) {
                    cityWindSpeed.setText(String.format(Locale.US, "%.1f km/h", convertToKmh(cityResult.getWind().getSpeed())));
                } else if (settings.getString("wind", "mph").equals("ms")) {
                    cityWindSpeed.setText(String.format(Locale.US, "%.1f m/s", cityResult.getWind().getSpeed()));
                } else {
                    cityWindSpeed.setText(String.format(Locale.US, "%.1f mph", convertToMph(cityResult.getWind().getSpeed())));
                }
            }
        });
    }

    private void getForecast() {
        viewModel.getForecastResult().observe(this, forecastResult -> {
            if (forecastResult.getForecast() != null && !forecastResult.getForecast().isEmpty()) {
                adapter.update(forecastResult.getForecast());
            } else {
                adapter.update(this.forecastList);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                        if (location != null) {
                            viewModel.getCityByLocation(location.getLatitude(), location.getLongitude());
                        }
                    });
                } else {
                    Toast.makeText(this, "Permission denied, set manually a city", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(this, MainActivity.class), AUTOCOMPLETE_REQUEST_CODE);
                }
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                startActivityForResult(new Intent(this, MainActivity.class), AUTOCOMPLETE_REQUEST_CODE);
                return true;
            case R.id.action_history:
                startActivity(new Intent(this, RecentCitiesActivity.class));
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putExtra(CITY_KEY, currentCity);
                startActivityForResult(intent, SETTINGS_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case SETTINGS_CODE:
                settings = PreferenceManager.getDefaultSharedPreferences(this);
                reloadPage();
                break;
            case AUTOCOMPLETE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    Double latitude = data.getDoubleExtra(LAT_KEY, 48.8587741);
                    Double longitude = data.getDoubleExtra(LON_KEY, 2.2069771);

                    viewModel.getCityByLocation(latitude, longitude);

                } else {
                    getLocationData();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void displayError() {
        viewModel.getError().observe(this, s -> {
            Snackbar.make(recyclerForecast, s, Snackbar.LENGTH_SHORT).show();
        });
    }

    private void displayLoading() {
        viewModel.getLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
                pageContent.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                pageContent.setVisibility(View.VISIBLE);
            }
        });
    }
}
