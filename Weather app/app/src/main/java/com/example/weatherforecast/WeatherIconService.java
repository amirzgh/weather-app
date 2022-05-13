package com.example.weatherforecast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class WeatherIconService {
    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getIcon(String condition , Context context){
        switch (condition) {
            case "clear sky":
                return context.getDrawable(R.drawable.ic_sun);
            case "few clouds":
            case "scattered clouds":
            case "broken clouds":
            case "overcast clouds":
                return context.getDrawable(R.drawable.ic_cloud);
            case "shower rain":
            case "light rain":
            case "rain":
                return context.getDrawable(R.drawable.ic_cloud_rain);
            case "thunderstorm":
                return context.getDrawable(R.drawable.ic_lightning);
            case "mist":
                return context.getDrawable(R.drawable.ic_tornado);
            default:
                return context.getDrawable(R.drawable.ic_baseline_cloud_24);
        }
    }
}
