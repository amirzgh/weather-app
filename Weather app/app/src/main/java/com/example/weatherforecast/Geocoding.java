package com.example.weatherforecast;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.Gravity;
import android.widget.Toast;

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

    public String getCityFromCoordinate(Double latitude, Double longitude) {
        //  Toast.makeText(context, "shit", Toast.LENGTH_SHORT).show();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        String result = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                if (addresses.get(0).getLocality() != null) {
                    result = addresses.get(0).getLocality();
                } else {
                    result = addresses.get(0).getAddressLine(0);
                }
            }
        } catch (Exception e) {

            try {
                Toast toast = Toast.makeText(context, "Invalid input!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            } catch (Exception e2) {
                // e2.printStackTrace();


            }

            // e.printStackTrace();
        }
        return result;
    }


}

