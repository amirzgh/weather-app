package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DBHelper weatherDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherDataBase = new DBHelper(MainActivity.this);
        weatherDataBase.insertData("Tehran", "1", "2", "3");
        weatherDataBase.insertData("london", "1", "2", "3");


        ////
        double g = 40.730610;
        double s = -73.935242;
        WeatherInfo.getWeatherInfoByCoordinates(g, s, getApplicationContext(), new WeatherInfoHandler());
////
        String coordinate = getCoordinate("Tehran");
        if (coordinate != null)
            Toast.makeText(getApplicationContext(), coordinate, Toast.LENGTH_SHORT).show();
    }

    private String getCoordinate(String cityName){
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        String result = null;
        try {
            List addressList = geocoder.getFromLocationName(cityName, 1);
            if (addressList != null && addressList.size() > 0){
                Address address = (Address) addressList.get(0);
                result = address.getLatitude() + "," + address.getLongitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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