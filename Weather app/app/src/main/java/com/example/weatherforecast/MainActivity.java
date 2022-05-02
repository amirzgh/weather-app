package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        weatherDataBase.insertData("Tehran", "1", "2", "3", "4", "5", "6", "7");
        weatherDataBase.insertData("london", "1", "2", "3", "4", "5", "6", "7");
        weatherDataBase.insertData("los angles", "1", "2", "3", "4", "5", "6", "7");
        weatherDataBase.insertData("new york", "1", "2", "3", "4", "5", "6", "7");


        ArrayList<String> arrayList = weatherDataBase.getDataByCityName("Tehran");
        if (arrayList != null) {
            for (String model : arrayList) {
                Toast.makeText(getApplicationContext(), model, Toast.LENGTH_SHORT).show();
            }
        }
//
//        String coordinate = getCoordinate("Tehran");
        //if (coordinate != null)
            //Toast.makeText(getApplicationContext(), coordinate, Toast.LENGTH_SHORT).show();
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


    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}