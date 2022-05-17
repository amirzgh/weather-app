package com.example.weatherforecast;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
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
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class HomePage extends Fragment {


    Button search_btn;
    public RadioButton byName_rbtn;
    RadioButton byGeo_rbtn;
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

    RecyclerView recyclerView;

    WeatherInfo weatherInfo = new WeatherInfo();


    ArrayList<ArrayList<String>> weatherResponse = new ArrayList<>();
    ArrayList<String> todayWeather = new ArrayList<>();
    WeatherIconService weatherIconService = new WeatherIconService();


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
        byGeo_rbtn = view.findViewById(R.id.byGeo_rbtn);
        search_btn = view.findViewById(R.id.search_btn);

        card_view = view.findViewById(R.id.card_view);
        weather_icon_image_view = view.findViewById(R.id.weather_icon_image_view);
        city_name_home_txt = view.findViewById(R.id.city_name_home_txt);
        feels_like_home_txt = view.findViewById(R.id.feels_like_home_txt);
        city_temperature_home_txt = view.findViewById(R.id.city_temperature_home_txt);
        wind_speed_home_txt = view.findViewById(R.id.wind_speed_home_txt);

        recyclerView = view.findViewById(R.id.week_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setVisibility(View.INVISIBLE);

        card_view.setVisibility(View.INVISIBLE);

        changeVisibility();

        byName_rbtn.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked = b;
                changeVisibility();
            }
        });

        byGeo_rbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked = !b;
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

    public void initRecyclerView(ArrayList<ArrayList<String>> weather){
        recyclerView.setAdapter(new RecyclerViewHandler(weather,CheckConnectivity.isOnline()));
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void setTodayWeather() {
        weather_icon_image_view.setImageDrawable(weatherIconService.getIcon(todayWeather.get(1),getContext()));
        city_temperature_home_txt.setText(todayWeather.get(2));
        feels_like_home_txt.setText(todayWeather.get(3));
        wind_speed_home_txt.setText(todayWeather.get(4));
    }

    public void setWeatherCache(){
        weather_icon_image_view.setImageDrawable(weatherIconService.getIcon(todayWeather.get(3),getContext()));
        city_temperature_home_txt.setText(todayWeather.get(4));
        feels_like_home_txt.setText(todayWeather.get(5));
        wind_speed_home_txt.setText(todayWeather.get(6));
        city_name_home_txt.setText(todayWeather.get(10));
    }


    public void search(){
        Geocoding geocoding1 = new Geocoding(getContext());
        DBHelper weatherDataBase = MainActivity.getWeatherDataBase();
        if (!isChecked) {
            if(CheckConnectivity.isOnline()) {
                Log.d("yes", "online : ");
                getWeather(Float.parseFloat(latitude_txt.getText().toString()), Float.parseFloat(longitude_txt.getText().toString())
                        , geocoding1.getCityFromCoordinate(Double.parseDouble(latitude_txt.getText().toString()), Double.parseDouble(longitude_txt.getText().toString())));
            } else{
                Log.d("no", "online : ");
                if(weatherDataBase.getDataFromDataBase(latitude_txt.getText().toString(), longitude_txt.getText().toString(), "0") == null){
                    //not in data base
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"there is no such data in cache",Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    ArrayList<ArrayList<String>> cityWeatherInfo = new ArrayList<>();
                    for (int i = 0; i < 8; i++) {
                        cityWeatherInfo.add(weatherDataBase.getDataFromDataBase(latitude_txt.getText().toString(), longitude_txt.getText().toString(), String.valueOf(i)));
                    }
                    weatherResponse = cityWeatherInfo;
                    todayWeather = weatherResponse.get(0);
                    ((MainActivity) requireContext()).runOnUiThread(new Runnable() {
                        public void run() {
                            setWeatherCache();
                            initRecyclerView(weatherResponse);
                            recyclerView.setVisibility(View.VISIBLE);
                            card_view.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        } else {
            if(CheckConnectivity.isOnline()) {
                Log.d("online", "search: ");
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
                } else{
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"City not found",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                if(weatherDataBase.getDataFromDataBase(city_text.getText().toString(), "0") == null){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"City not found in data base",Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    ArrayList<ArrayList<String>> cityWeatherInfo = new ArrayList<>();
                    for (int i = 0; i < 8; i++) {
                        cityWeatherInfo.add(weatherDataBase.getDataFromDataBase(city_text.getText().toString(), String.valueOf(i)));
                    }
                    weatherResponse = cityWeatherInfo;
                    todayWeather = weatherResponse.get(0);
                    ((MainActivity) requireContext()).runOnUiThread(new Runnable() {
                        public void run() {
                            setWeatherCache();
                            initRecyclerView(weatherResponse);
                            recyclerView.setVisibility(View.VISIBLE);
                            card_view.setVisibility(View.VISIBLE);
                        }
                    });
                    Log.d(String.valueOf(weatherResponse), "search: ");
                    Log.d("today", String.valueOf(todayWeather));
                    Log.d("success", String.valueOf(weatherResponse));
                }
            }
        }
    }

    public void getWeather(float latitude, float longitude, String cityName) {
        weatherInfo.getWeatherInfoByCoordinates(latitude, longitude, getContext(), new VolleyCallback() {
            @Override
            public void onSuccessfulResponse(ArrayList<ArrayList<String>> result) {
                Geocoding geocoding = new Geocoding(getContext());
                DBHelper weatherDataBase = MainActivity.getWeatherDataBase();
                weatherResponse = result;
                todayWeather = weatherResponse.get(0);
                setTodayWeather();
                city_name_home_txt.setText(cityName);
                initRecyclerView(weatherResponse);
                recyclerView.setVisibility(View.VISIBLE);
                card_view.setVisibility(View.VISIBLE);
                Log.d("success", String.valueOf(weatherResponse));
                for(ArrayList<String> s : result) {
                    weatherDataBase.insertData(String.valueOf(latitude), String.valueOf(longitude),
                            s.get(0),s.get(1),s.get(2),s.get(3),s.get(4), s.get(5),s.get(6), s.get(7),
                            cityName, String.valueOf(LocalTime.now()));
                }
            }

            @Override
            public void onErrorOccurredResponse(ArrayList<ArrayList<String>> result) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"Please Enter Valid input",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

}