package com.example.weatherforecast;

import java.util.ArrayList;

public interface VolleyCallback {
    void onSuccessfulResponse(ArrayList<ArrayList<String>> result);
    void onErrorOccurredResponse(ArrayList<ArrayList<String>> result);
}
