package com.example.weatherforecast;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Geocoding {

    private final Context context;
    public Geocoding(Context context) {
        this.context = context;
    }

    public Double[] getCoordinate(String cityName) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        Double[] result = new Double[3];
        result[2] = 0.0;
        try {
            List<Address> addressList = geocoder.getFromLocationName(cityName, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = (Address) addressList.get(0);
                result[0] = address.getLatitude();
                result[1] = address.getLongitude();
                result[2] = 1.0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getCityFromCoordinate(Double latitude, Double longitude){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses != null ? addresses.get(0).getLocality() : null;
    }

}
