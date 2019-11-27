package com.example.androidweatherchallenge.model.autocomplete;

public class PlaceAutoComplete {

    public CharSequence placeId;
    public CharSequence city, stateCountry;

    public PlaceAutoComplete(CharSequence placeId, CharSequence city, CharSequence stateCountry) {
        this.placeId = placeId;
        this.city = city;
        this.stateCountry = stateCountry;
    }

    public CharSequence getPlaceId() {
        return placeId;
    }

    public void setPlaceId(CharSequence placeId) {
        this.placeId = placeId;
    }

    public CharSequence getCity() {
        return city;
    }

    public void setCity(CharSequence city) {
        this.city = city;
    }

    public CharSequence getStateCountry() {
        return stateCountry;
    }

    public void setStateCountry(CharSequence stateCountry) {
        this.stateCountry = stateCountry;
    }
}
