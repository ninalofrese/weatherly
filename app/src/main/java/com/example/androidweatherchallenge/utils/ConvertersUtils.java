package com.example.androidweatherchallenge.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConvertersUtils {

    public static String convertTime(Long timestamp) {
        Locale local = new Locale("pt", "BR");
        SimpleDateFormat sdf = new SimpleDateFormat("HH'h'mm", local);

        String time = sdf.format(timestamp * 1000);

        return time;
    }

    public static String convertDate(Long timestamp) {
        Locale local = new Locale("pt", "BR");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", local);

        String date = sdf.format(timestamp * 1000);

        return date;
    }

    public static Double convertToKmh(Double meterBySecond) {
        return meterBySecond * 3.6;
    }

    public static Double convertToMph(Double meterBySecond) {
        return meterBySecond * 2.237;
    }

    public static String convertWindDirection(Long degree) {
        if (degree > 337.5) return "N";
        if (degree > 292.5) return "NW";
        if (degree > 247.5) return "W";
        if (degree > 202.5) return "SW";
        if (degree > 157.5) return "S";
        if (degree > 122.5) return "SE";
        if (degree > 67.5) return "E";
        if (degree > 22.5) return "NE";

        return "N";
    }
}
