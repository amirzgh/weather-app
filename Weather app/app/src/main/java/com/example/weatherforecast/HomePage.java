package com.example.weatherforecast;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class HomePage extends Fragment {
    Button search_btn;
    public RadioButton byName_rbtn;
    EditText longitude_txt;
    EditText latitude_txt;
    EditText city_text;
    Boolean isChecked = false;

    CardView card_view;
    ImageView weather_icon_image_view;
    TextView city_name_home_txt;
    TextView feels_like_home_txt;
    TextView city_temperature_home_txt;
    TextView wind_speed_home_txt;

    WeatherInfo weatherInfo = new WeatherInfo();


    ArrayList<ArrayList<String>> weatherResponse = new ArrayList<>();
    ArrayList<String> todayWeather = new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void changeVisibility() {
        if (!isChecked) {
            city_text.setVisibility(View.INVISIBLE);
            latitude_txt.setVisibility(View.VISIBLE);
            longitude_txt.setVisibility(View.VISIBLE);
        } else {
            city_text.setVisibility(View.VISIBLE);
            latitude_txt.setVisibility(View.INVISIBLE);
            longitude_txt.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        city_text = view.findViewById(R.id.city_txt);
        latitude_txt = view.findViewById(R.id.latitude_txt);
        longitude_txt = view.findViewById(R.id.longitude_txt);
        byName_rbtn = view.findViewById(R.id.byName_rbtn);
        search_btn = view.findViewById(R.id.search_btn);

        card_view = view.findViewById(R.id.card_view);
        weather_icon_image_view = view.findViewById(R.id.weather_icon_image_view);
        city_name_home_txt = view.findViewById(R.id.city_name_home_txt);
        feels_like_home_txt = view.findViewById(R.id.feels_like_home_txt);
        city_temperature_home_txt = view.findViewById(R.id.city_temperature_home_txt);
        wind_speed_home_txt = view.findViewById(R.id.wind_speed_home_txt);

        card_view.setVisibility(View.INVISIBLE);

        changeVisibility();

        byName_rbtn.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked = b;
                changeVisibility();
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        city_text.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }
                    Timer timer = new Timer();
                    long DELAY = 5000; //ms
                    @Override
                    public void afterTextChanged(Editable editable) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        search();
                                    }
                                },
                                DELAY
                        );
                    }
                }
        );

        longitude_txt.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    private Timer timer = new Timer();
                    private final long DELAY = 5000; //ms

                    @Override
                    public void afterTextChanged(Editable editable) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        search();
                                    }
                                },
                                DELAY
                        );
                    }
                }
        );
        return view;
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void setTodayWeather() {
        weather_icon_image_view.setImageDrawable(getIcon(todayWeather.get(1)));
        city_temperature_home_txt.setText(todayWeather.get(2));
        feels_like_home_txt.setText(todayWeather.get(3));
        wind_speed_home_txt.setText(todayWeather.get(4));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getIcon(String condition) {
        switch (condition) {
            case "clear sky":
                return getResources().getDrawable(R.drawable.ic_sun);
            case "few clouds":
            case "scattered clouds":
            case "broken clouds":
            case "overcast clouds":
                return getResources().getDrawable(R.drawable.ic_cloud);
            case "shower rain":
            case "rain":
                return getResources().getDrawable(R.drawable.ic_cloud_rain);
            case "thunderstorm":
                return getResources().getDrawable(R.drawable.ic_lightning);
            case "mist":
                return getResources().getDrawable(R.drawable.ic_tornado);
            default:
                return getResources().getDrawable(R.drawable.ic_baseline_cloud_24);
        }
    }

    public void search(){
        Geocoding geocoding1 = new Geocoding(getContext());
        if (!isChecked) {
            getWeather(Float.parseFloat(latitude_txt.getText().toString()), Float.parseFloat(longitude_txt.getText().toString())
                    , geocoding1.getCityFromCoordinate(Double.parseDouble(latitude_txt.getText().toString()), Double.parseDouble(longitude_txt.getText().toString())));
        } else {
            String city = city_text.getText().toString();
            Double[] coordinate = geocoding1.getCoordinate(city);
            Double latitude;
            Double longitude;

            if (coordinate[2] == 1.0) {
                latitude = coordinate[0];
                longitude = coordinate[1];
                getWeather(Float.parseFloat(String.valueOf(latitude))
                        , Float.parseFloat(String.valueOf(longitude)),
                        city);
            }


        }
    }

    public void getWeather(float latitude, float longitude, String cityName) {
        weatherInfo.getWeatherInfoByCoordinates(latitude, longitude, getContext(), new VolleyCallback() {
            @Override
            public void onSuccessfulResponse(ArrayList<ArrayList<String>> result) {
                Log.d("in", "onSuccessfulResponse: ");
                weatherResponse = result;
                todayWeather = weatherResponse.get(0);
                setTodayWeather();
                city_name_home_txt.setText(cityName);
                card_view.setVisibility(View.VISIBLE);
                Log.d("result", String.valueOf(todayWeather));
            }
        });
    }

}