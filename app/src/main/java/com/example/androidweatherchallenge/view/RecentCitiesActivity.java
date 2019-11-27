package com.example.androidweatherchallenge.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidweatherchallenge.R;
import com.example.androidweatherchallenge.model.city.CityResult;
import com.example.androidweatherchallenge.view.adapters.RecyclerRecentCityAdapter;
import com.example.androidweatherchallenge.view.interfaces.SelectRecentCity;
import com.example.androidweatherchallenge.viewmodel.RecentCitiesActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.example.androidweatherchallenge.view.CityActivity.AUTOCOMPLETE_REQUEST_CODE;
import static com.example.androidweatherchallenge.view.MainActivity.CITY_KEY;

public class RecentCitiesActivity extends AppCompatActivity implements SelectRecentCity {
    private RecyclerView recyclerCities;
    private List<CityResult> cityResults = new ArrayList<>();
    private RecyclerRecentCityAdapter adapter;
    private RecentCitiesActivityViewModel viewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_cities);
        Toolbar toolbar = findViewById(R.id.toolbar_recent);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Recent Cities");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initViews();

        viewModel.getLocalCities();

        viewModel.getCities().observe(this, results -> {
            if (results != null && !results.isEmpty()) {
                adapter.updateList(results);
            } else {
                adapter.updateList(this.cityResults);
            }
        });

        displayError();
        displayLoading();

    }

    private void initViews() {
        recyclerCities = findViewById(R.id.recycler_cities_recent);
        progressBar = findViewById(R.id.progress_bar_recent);
        viewModel = ViewModelProviders.of(this).get(RecentCitiesActivityViewModel.class);
        adapter = new RecyclerRecentCityAdapter(cityResults, this);
        recyclerCities.setLayoutManager(new LinearLayoutManager(this));
        recyclerCities.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(CityResult city) {
        Intent intent = new Intent(this, CityActivity.class);
        intent.putExtra(CITY_KEY, city);
        startActivity(intent);
    }

    private void displayError() {
        viewModel.getError().observe(this, s -> {
            Snackbar.make(recyclerCities, s, Snackbar.LENGTH_SHORT).show();
        });
    }

    private void displayLoading() {
        viewModel.getLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
