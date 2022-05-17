package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static DBHelper weatherDataBase;
    Geocoding geocoding;
    TabLayout tabLayout;
    Fragment fragment = new HomePage();


    public static DBHelper getWeatherDataBase() {
        return weatherDataBase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //check condition for dark_mode
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Dark);
        } else setTheme(R.style.Theme_Light);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherDataBase = new DBHelper(MainActivity.this);
        geocoding = new Geocoding(MainActivity.this);

        tabLayout = findViewById(R.id.tabLayout);
        setTabFragment(fragment);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new HomePage();
                        break;
                    case 1:
                        fragment = new setting();
                        break;
                }
                setTabFragment(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void setTabFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        assert fragment != null;
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}