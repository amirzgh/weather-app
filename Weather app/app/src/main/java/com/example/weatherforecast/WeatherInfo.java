package com.example.weatherforecast;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WeatherInfo {
    //40.730610,	-73.935242

    public void getWeatherInfoByCoordinates(double latitude, double longitude, Context context, final VolleyCallback callback) {
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
                        cityWeatherInfo.get(i).add(String.valueOf(i));

                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(i);
                        JSONObject weather = jsonObjectWeather.getJSONArray("weather").getJSONObject(0);
                        String description = weather.getString("description");
                        cityWeatherInfo.get(i).add(String.valueOf(description));//0

                        JSONObject jsonObjectMain = jsonObjectWeather.getJSONObject("main");

                        double temper = jsonObjectMain.getDouble("temp");
                        cityWeatherInfo.get(i).add(String.valueOf(temper));//1

                        double feelsLike = jsonObjectMain.getDouble("feels_like");
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

                        if (i == 7) {
                            callback.onSuccessfulResponse(cityWeatherInfo);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ArrayList<ArrayList<String>> cityWeatherInfoError = new ArrayList<>(8);

                for (int i = 0; i < 8; i++) {
                    cityWeatherInfoError.add(new ArrayList<>(7));
                }
                cityWeatherInfoError.get(0).add("onErrorResponse");
                callback.onSuccessfulResponse(cityWeatherInfoError);
                System.out.println("  onErrorResponse  )):");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


}
