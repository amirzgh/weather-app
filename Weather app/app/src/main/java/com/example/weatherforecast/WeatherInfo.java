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
    ///40.730610,	-73.935242

    static ArrayList<String> cityWeatherInfo = new ArrayList<>();

    public static ArrayList<String> getWeatherInfoByCoordinates(double latitude, double longitude, Context context) {

        String apiKey = "60d02a8e8559a6937eb7f31c672e18ba";
        String tempUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");

                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    cityWeatherInfo.add(String.valueOf(description));//0

                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temper = jsonObjectMain.getDouble("temp") - 273.15;
                    cityWeatherInfo.add(String.valueOf(temper));//1

                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                    cityWeatherInfo.add(String.valueOf(feelsLike));//2

                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");
                    cityWeatherInfo.add(String.valueOf(wind));//3


                    float pressure = jsonObjectMain.getInt("pressure");
                    cityWeatherInfo.add(String.valueOf(pressure));//4

                    int humidity = jsonObjectMain.getInt("humidity");
                    cityWeatherInfo.add(String.valueOf(humidity));//5

                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");
                    cityWeatherInfo.add(String.valueOf(clouds));//6
                    ////
                    System.out.println(clouds + "----------------------------------clouds");
                    System.out.println(humidity + "----------------------------------humidity");
                    System.out.println(cityWeatherInfo.get(0) + "----------------------------------cityWeatherInfo");
///


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
