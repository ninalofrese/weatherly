package com.example.androidweatherchallenge.view.adapters;

import android.content.Context;
import android.text.style.CharacterStyle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidweatherchallenge.R;
import com.example.androidweatherchallenge.model.autocomplete.PlaceAutoComplete;
import com.example.androidweatherchallenge.view.interfaces.SelectCityListener;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RecyclerAutoCompleteAdapter extends RecyclerView.Adapter<RecyclerAutoCompleteAdapter.ViewHolder> implements Filterable {
    private CharacterStyle STYLE_BOLD;
    private CharacterStyle STYLE_NORMAL;
    private List<PlaceAutoComplete> predictionList = new ArrayList<>();
    private Context context;
    private SelectCityListener listener;
    private final PlacesClient placesClient;

    public RecyclerAutoCompleteAdapter(Context context) {
        this.context = context;
        placesClient = com.google.android.libraries.places.api.Places.createClient(context);
    }

    public void setClickListener(SelectCityListener listener) {
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if (charSequence != null) {
                    predictionList = getPredictions(charSequence);
                    if (predictionList != null) {
                        results.values = predictionList;
                        results.count = predictionList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                }
            }
        };
    }

    private List<PlaceAutoComplete> getPredictions(CharSequence charSequence) {
        final List<PlaceAutoComplete> resultList = new ArrayList<>();

        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setTypeFilter(TypeFilter.REGIONS)
                .setSessionToken(token)
                .setQuery(charSequence.toString())
                .build();

        Task<FindAutocompletePredictionsResponse> autocompletePredictions = placesClient.findAutocompletePredictions(request);

        try {
            Tasks.await(autocompletePredictions, 60, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        if (autocompletePredictions.isSuccessful()) {
            FindAutocompletePredictionsResponse findAutocompletePredictionsResponse = autocompletePredictions.getResult();
            if (findAutocompletePredictionsResponse != null)
                for (AutocompletePrediction prediction : findAutocompletePredictionsResponse.getAutocompletePredictions()) {
                    resultList.add(new PlaceAutoComplete(prediction.getPlaceId(), prediction.getPrimaryText(STYLE_NORMAL).toString(), prediction.getFullText(STYLE_BOLD).toString()));
                }

            return resultList;
        } else {
            return resultList;
        }

    }

    @NonNull
    @Override
    public RecyclerAutoCompleteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(R.layout.item_prediction_result, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAutoCompleteAdapter.ViewHolder holder, int position) {
        PlaceAutoComplete prediction = predictionList.get(position);
        holder.onBind(prediction);
    }

    @Override
    public int getItemCount() {
        return predictionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView place;
        private TextView detail;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            place = itemView.findViewById(R.id.text_prediction_place);
            detail = itemView.findViewById(R.id.text_prediction_detail);
            cardView = itemView.findViewById(R.id.item_prediction_row);
            itemView.setOnClickListener(this);
        }

        public void onBind(PlaceAutoComplete prediction) {
            place.setText(prediction.getCity());
            detail.setText(prediction.getStateCountry());

        }

        @Override
        public void onClick(View view) {
            PlaceAutoComplete item = predictionList.get(getAdapterPosition());
            if (view.getId() == R.id.item_prediction_row) {

                String placeId = String.valueOf(item.placeId);

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
                FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
                placesClient.fetchPlace(request).addOnSuccessListener(response -> {
                    Place place = response.getPlace();
                    listener.clickCity(place);
                }).addOnFailureListener(exception -> {
                    if (exception instanceof ApiException) {
                        Toast.makeText(context, exception.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
