package com.example.androidweatherchallenge.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidweatherchallenge.R;
import com.example.androidweatherchallenge.view.adapters.RecyclerAutoCompleteAdapter;
import com.example.androidweatherchallenge.view.interfaces.SelectCityListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;

import static com.example.androidweatherchallenge.utils.AppConstants.GOOGLE_API_KEY;

public class MainActivity extends AppCompatActivity implements SelectCityListener {
    public static final String LON_KEY = "longitude";
    public static final String LAT_KEY = "latitude";
    public static final String CITY_KEY = "cidade";
    private EditText searchInput;
    private RecyclerView recyclerCities;
    private RecyclerAutoCompleteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), GOOGLE_API_KEY);
        }

        initViews();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        searchInput.addTextChangedListener(filterTextWatcher);

    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                adapter.getFilter().filter(s.toString());
                if (recyclerCities.getVisibility() == View.GONE) {
                    recyclerCities.setVisibility(View.VISIBLE);
                }
            } else {
                if (recyclerCities.getVisibility() == View.VISIBLE) {
                    recyclerCities.setVisibility(View.GONE);
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    private void initViews() {
        searchInput = findViewById(R.id.intro_city_search);
        recyclerCities = findViewById(R.id.recycler_city_autocomplete);
        adapter = new RecyclerAutoCompleteAdapter(this);
        recyclerCities.setLayoutManager(new LinearLayoutManager(this));
        recyclerCities.addItemDecoration(new DividerItemDecoration(recyclerCities.getContext(),
                DividerItemDecoration.VERTICAL));
        adapter.setClickListener(this);
        recyclerCities.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clickCity(Place place) {
        Intent intent = new Intent();
        intent.putExtra(LAT_KEY, place.getLatLng().latitude);
        intent.putExtra(LON_KEY, place.getLatLng().longitude);
        setResult(RESULT_OK, intent);
        finish();
    }

}


