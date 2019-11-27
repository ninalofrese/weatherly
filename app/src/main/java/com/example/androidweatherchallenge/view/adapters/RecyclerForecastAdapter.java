package com.example.androidweatherchallenge.view.adapters;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidweatherchallenge.R;
import com.example.androidweatherchallenge.model.forecast.Forecast;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import static com.example.androidweatherchallenge.utils.ConvertersUtils.convertDate;
import static com.example.androidweatherchallenge.utils.ConvertersUtils.convertTime;
import static com.example.androidweatherchallenge.utils.ConvertersUtils.convertToKmh;
import static com.example.androidweatherchallenge.utils.ConvertersUtils.convertToMph;
import static com.example.androidweatherchallenge.utils.ConvertersUtils.convertWindDirection;

public class RecyclerForecastAdapter extends RecyclerView.Adapter<RecyclerForecastAdapter.ViewHolder> {
    private List<Forecast> forecastList;

    public RecyclerForecastAdapter(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Forecast forecast = forecastList.get(position);
        holder.onBind(forecast);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public void update(List<Forecast> forecastList) {
        if (this.forecastList.isEmpty()) {
            this.forecastList = forecastList;
        } else {
            this.forecastList.addAll(forecastList);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView forecastDate;
        private TextView forecastHour;
        private ImageView forecastIcon;
        private TextView forecastClouds;
        private TextView forecastTemperature;
        private TextView forecastTempMax;
        private TextView forecastTempMin;
        private TextView forecastHumidity;
        private TextView forecastWindDirection;
        private TextView forecastWindSpeed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            forecastDate = itemView.findViewById(R.id.text_forecast_date);
            forecastHour = itemView.findViewById(R.id.text_forecast_hour);
            forecastIcon = itemView.findViewById(R.id.icon_forecast_weather);
            forecastClouds = itemView.findViewById(R.id.text_forecast_clouds);
            forecastTemperature = itemView.findViewById(R.id.text_forecast_temperature);
            forecastTempMax = itemView.findViewById(R.id.text_forecast_tempmax);
            forecastTempMin = itemView.findViewById(R.id.text_forecast_tempmin);
            forecastHumidity = itemView.findViewById(R.id.text_forecast_humidity);
            forecastWindDirection = itemView.findViewById(R.id.text_forecast_winddirection);
            forecastWindSpeed = itemView.findViewById(R.id.text_forecast_windspeed);
        }

        public void onBind(Forecast forecast) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(itemView.getContext());
            String iconUri = "http://openweathermap.org/img/wn/" + forecast.getWeather().get(0).getIcon() + "@2x.png";
            Picasso.get().load(iconUri).into(forecastIcon);
            forecastDate.setText(convertDate(forecast.getDt()));
            forecastHour.setText(convertTime(forecast.getDt()));
            forecastClouds.setText(String.format(Locale.US, "%d%%", forecast.getClouds().getAll()));
            forecastTemperature.setText(String.format(Locale.US, "%.0f\u00B0", forecast.getMain().getTemp()));
            forecastTempMax.setText(String.format(Locale.US, "%.0f\u00B0", forecast.getMain().getTempMax()));
            forecastTempMin.setText(String.format(Locale.US, "%.0f\u00B0", forecast.getMain().getTempMin()));
            forecastHumidity.setText(String.format(Locale.US, "%d%%", forecast.getMain().getHumidity()));
            forecastWindDirection.setText(convertWindDirection(forecast.getWind().getDeg()));
            if (settings.getString("wind", "-1").equals("kmh")) {
                forecastWindSpeed.setText(String.format(Locale.US, "%.1f km/h", convertToKmh(forecast.getWind().getSpeed())));
            } else if (settings.getString("wind", "-1").equals("ms")) {
                forecastWindSpeed.setText(String.format(Locale.US, "%.1f m/s", forecast.getWind().getSpeed()));
            } else {
                forecastWindSpeed.setText(String.format(Locale.US, "%.1f mph", convertToMph(forecast.getWind().getSpeed())));
            }
        }
    }
}
