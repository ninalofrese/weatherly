package com.example.androidweatherchallenge.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidweatherchallenge.R;
import com.example.androidweatherchallenge.model.city.CityResult;
import com.example.androidweatherchallenge.view.interfaces.SelectRecentCity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class RecyclerRecentCityAdapter extends RecyclerView.Adapter<RecyclerRecentCityAdapter.ViewHolder> {
    private List<CityResult> cityResults;
    private SelectRecentCity listener;

    public RecyclerRecentCityAdapter(List<CityResult> cityResults, SelectRecentCity listener) {
        this.cityResults = cityResults;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CityResult result = cityResults.get(position);
        holder.onBind(result);
    }

    @Override
    public int getItemCount() {
        return cityResults.size();
    }

    public void updateList(List<CityResult> results) {
        if (this.cityResults.isEmpty()) {
            this.cityResults = results;
        } else {
            this.cityResults.addAll(results);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cityName;
        private TextView cityDetails;
        private ImageView cityIcon;
        private TextView cityTemperature;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.text_recent_name);
            cityDetails = itemView.findViewById(R.id.text_recent_details);
            cityIcon = itemView.findViewById(R.id.icon_recent_weather);
            cityTemperature = itemView.findViewById(R.id.text_recent_temperature);

        }

        public void onBind(CityResult result) {
            String iconUri = "http://openweathermap.org/img/wn/" + result.getWeather().get(0).getIcon() + "@2x.png";
            cityName.setText(result.getName());
            cityDetails.setText(result.getSys().getCountry());
            Picasso.get().load(iconUri).into(cityIcon);
            cityTemperature.setText(String.format(Locale.US, "%.0f", result.getMain().getTemp()));

            itemView.setOnClickListener(view -> {
                listener.onClick(result);
            });
        }
    }
}
