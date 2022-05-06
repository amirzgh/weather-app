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
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DBHelper weatherDataBase;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //check condition for dark_mode
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Dark);
        } else setTheme(R.style.Theme_Light);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        weatherDataBase = new DBHelper(MainActivity.this);
//        weatherDataBase.insertData("Tehran", "1", "2", "3", "4", "5", "6", "7");
//        weatherDataBase.insertData("london", "1", "2", "3", "4", "5", "6", "7");
//        weatherDataBase.insertData("los angles", "1", "2", "3", "4", "5", "6", "7");
//        weatherDataBase.insertData("new york", "1", "2", "3", "4", "5", "6", "7");
//
//        System.out.println(CheckConnectivity.isOnline() + " +++++++++++++++++++++++++++++++++++++++++");
//        ArrayList<String> arrayList = weatherDataBase.getDataByCityName("Tehran");
//        if (arrayList != null) {
//            for (String model : arrayList) {
//                Toast.makeText(getApplicationContext(), model, Toast.LENGTH_SHORT).show();
//            }
//        }


        ////when you want to get weather info copy this pieace of code (put instead of 40.730610 the latitude and instead of -73.935242 the longitude)
        new WeatherInfo().getWeatherInfoByCoordinates(40.730610, -73.935242, getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccessfulResponse(ArrayList<ArrayList<String>> result) {
                System.out.println(result+" ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^6");
                //get result of weather here ...
            }
        });
        ///

        String coordinate = getCoordinate("Tehran");
        if (coordinate != null)
            Toast.makeText(getApplicationContext(), coordinate, Toast.LENGTH_SHORT).show();

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new HomePage();
                        break;
                    case 1:
                        fragment = new setting();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                assert fragment != null;
                fragmentTransaction.replace(R.id.main, fragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private String getCoordinate(String cityName) {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        String result = null;
        try {
            List addressList = geocoder.getFromLocationName(cityName, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = (Address) addressList.get(0);
                result = address.getLatitude() + "," + address.getLongitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}