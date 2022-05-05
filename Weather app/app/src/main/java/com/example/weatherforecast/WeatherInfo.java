package com.example.weatherforecast;

import android.content.Context;
import android.net.Uri;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeatherInfo {
    ///40.730610,	-73.935242


    public ArrayList<ArrayList<String>> getWeatherInfoByCoordinates(double latitude, double longitude, Context context) {
        ArrayList<ArrayList<String>> cityWeatherInfo = new ArrayList<>(8);
        String apiKey = "60d02a8e8559a6937eb7f31c672e18ba";
        String tempUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&cnt=" + 8 + "&appid=" + apiKey + "&units=metric";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                for (int i = 0; i < 8; i++) {
                    cityWeatherInfo.add(new ArrayList<>(7));
                }

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(i);
                        JSONObject weather = jsonObjectWeather.getJSONArray("weather").getJSONObject(0);
                        String description = weather.getString("description");
                        cityWeatherInfo.get(i).add(String.valueOf(description));//0

                        JSONObject jsonObjectMain = jsonObjectWeather.getJSONObject("main");

                        double temper = jsonObjectMain.getDouble("temp") - 273.15;
                        cityWeatherInfo.get(i).add(String.valueOf(temper));//1


                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        cityWeatherInfo.get(i).add(String.valueOf(feelsLike));//2

                        JSONObject jsonObjectWind = jsonObjectWeather.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        cityWeatherInfo.get(i).add(String.valueOf(wind));//3


                        float pressure = jsonObjectMain.getInt("pressure");
                        cityWeatherInfo.get(i).add(String.valueOf(pressure));//4

                        int humidity = jsonObjectMain.getInt("humidity");
                        cityWeatherInfo.get(i).add(String.valueOf(humidity));//5

                        JSONObject jsonObjectClouds = jsonObjectWeather.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        cityWeatherInfo.get(i).add(String.valueOf(clouds));//6


                        ////
                        System.out.println(cityWeatherInfo.get(i).get(1) + "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        for (int j = 0; j < 7; j++) {
                            System.out.println(cityWeatherInfo.get(i).get(j) + "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        }
                        System.out.println(i + " =day--------------------------------------------------------");
                        ////


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

        return cityWeatherInfo;
    }


}
