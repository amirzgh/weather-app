package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherDayInformation extends AppCompatActivity {

    ImageView day_weather_icon_image_view;
    TextView day_city_name_home_txt;
    TextView day_feels_like_home_txt;
    TextView day_city_temperature_home_txt;
    TextView day_wind_speed_home_txt;
    TextView day_clouds, day_humidity, day_pressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_day_information);

        Intent intent = getIntent();
        String description = intent.getStringExtra("description");
        String speed = intent.getStringExtra("speed");
        String pressure = intent.getStringExtra("pressure");
        String temperature = intent.getStringExtra("temperature");
        String feels_like = intent.getStringExtra("feels_like");
        String humidity = intent.getStringExtra("humidity");
        String clouds = intent.getStringExtra("clouds");
        String cityName = intent.getStringExtra("cityName");

        day_weather_icon_image_view = findViewById(R.id.day_weather_icon_image_view);
        day_city_name_home_txt = findViewById(R.id.day_city_name_home_txt);
        day_feels_like_home_txt = findViewById(R.id.day_feels_like_home_txt);
        day_city_temperature_home_txt = findViewById(R.id.day_city_temperature_home_txt);
        day_wind_speed_home_txt = findViewById(R.id.day_wind_speed_home_txt);
        day_clouds = findViewById(R.id.day_clouds_home_txt);
        day_humidity = findViewById(R.id.day_humidity_home_txt);
        day_pressure = findViewById(R.id.day_pressure_home_txt);

        WeatherIconService weatherIconService = new WeatherIconService();
        day_weather_icon_image_view.setImageDrawable(weatherIconService.getIcon(description, getApplicationContext()));
        day_city_name_home_txt.setText(cityName);
        day_feels_like_home_txt.setText(feels_like);
        day_city_temperature_home_txt .setText(temperature);
        day_wind_speed_home_txt.setText(speed);
        day_clouds.setText(clouds);
        day_humidity.setText(humidity);
        day_pressure.setText(pressure);

    }

//    public void setTodayWeather() {
////        weather_icon_image_view.setImageDrawable(getIcon(todayWeather.get(1)));
//        day_weather_icon_image_view.setImageDrawable(weatherIconService.getIcon(todayWeather.get(1),getContext()));
//        day_city_temperature_home_txt.setText(todayWeather.get(2));
//        day_feels_like_home_txt.setText(todayWeather.get(3));
//        day_wind_speed_home_txt.setText(todayWeather.get(4));
//    }
}