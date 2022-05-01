package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////
        double g = 40.730610;
        double s = -73.935242;
        WeatherInfo.getWeatherInfoByCoordinates(g, s, getApplicationContext(), new WeatherInfoHandler());
////
        GeoLocation geoLocation = new GeoLocation();
        geoLocation.getAddress("a", getApplicationContext(), new GeoHandler());
    }

    private class GeoHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String address;
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    address = bundle.getString("address");
                    break;
                default:
                    address = null;
            }
            if (address != null) {
                Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class WeatherInfoHandler extends Handler {
        public static ArrayList<String> weather = new ArrayList<>();

        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            weather.clear();
            weather = bundle.getStringArrayList("cityWeatherInfo");
//            for (int i = 0; i < 6; i++) {
//                System.out.println(weather.get(i) + " ----------------------------------------------------------------\n\n\n");
//
//            }
        }
    }
}