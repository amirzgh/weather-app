package com.example.weatherforecast;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class WeatherInfo {
    ///40.730610,	-73.935242


    public void getWeatherInfoByCoordinates(double latitude, double longitude, Context context, DBHelper dbHelper) {

        String apiKey = "60d02a8e8559a6937eb7f31c672e18ba";
        String tempUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&cnt=" + 8 + "&appid=" + apiKey + "&units=metric";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(i);
                        JSONObject weather = jsonObjectWeather.getJSONArray("weather").getJSONObject(0);
                        String description = weather.getString("description");

                        JSONObject jsonObjectMain = jsonObjectWeather.getJSONObject("main");

                        double temper = jsonObjectMain.getDouble("temp") - 273.15;


                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;

                        JSONObject jsonObjectWind = jsonObjectWeather.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");


                        float pressure = jsonObjectMain.getInt("pressure");

                        int humidity = jsonObjectMain.getInt("humidity");

                        JSONObject jsonObjectClouds = jsonObjectWeather.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");

                        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (addresses != null) {
                            String cityName = addresses.get(0).getAddressLine(0);
                            dbHelper.insertData(String.valueOf(latitude),
                                    String.valueOf(longitude),
                                    description,
                                    wind,
                                    String.valueOf(pressure),
                                    String.valueOf(temper),
                                    String.valueOf(feelsLike),
                                    String.valueOf(humidity),
                                    clouds,
                                    cityName);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


}
