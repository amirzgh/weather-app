package com.example.weatherforecast;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckConnectivity {


    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                System.out.println("trueeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                return true;
            }

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                System.out.println("trueeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

                return true;
            }
        }
        System.out.println("falsssssssssssssssssssssssssssssssssssssssssssss");

        return false;
    }


}
