package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DBHelper weatherDataBase;
    Geocoding geocoding;
    TabLayout tabLayout;
    Fragment fragment = new HomePage();
    double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        //check condition for dark_mode
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Dark);
        } else setTheme(R.style.Theme_Light);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherDataBase = new DBHelper(MainActivity.this);
        geocoding = new Geocoding(MainActivity.this);

        Double[] coordinate = geocoding.getCoordinate("delhi");

        if(coordinate[2] == 1.0) {
            latitude = coordinate[0];
            longitude = coordinate[1];
        }


        Toast.makeText(getApplicationContext(), geocoding.getCityFromCoordinate(latitude, longitude), Toast.LENGTH_LONG).show();
        ////when you want to get weather info copy this pieace of code (put instead of 40.730610 the latitude and instead of -73.935242 the longitude)
        new WeatherInfo().getWeatherInfoByCoordinates(latitude, longitude, getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccessfulResponse(ArrayList<ArrayList<String>> result) {
                if(result != null){
                    for(ArrayList<String> s : result) {
                        weatherDataBase.insertData(String.valueOf(latitude), String.valueOf(longitude),
                                s.get(0),s.get(1),s.get(2),s.get(3),s.get(4), s.get(5),s.get(6), s.get(7),
                                geocoding.getCityFromCoordinate(latitude, longitude), String.valueOf(LocalTime.now()));
                    }
                }
            }
        });

        tabLayout = findViewById(R.id.tabLayout);
        setTabFragment(fragment);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new HomePage();
                        break;
                    case 1:
                        fragment = new setting();
                        break;
                }
                setTabFragment(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void setTabFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        assert fragment != null;
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}