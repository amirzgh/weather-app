package com.example.weatherforecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "WeatherDatabase";
    public static final String TableName = "WeatherInfo";


    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table WeatherInfo(latitude text,longitude text, numDay text, description text, speed text, pressure text, temperature text, feels_like text, humidity text, clouds text,cityName text, reqHour text, primary key (latitude, longitude, numDay))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists WeatherInfo");
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String latitude, String longitude, String numDay, String description, String speed, String pressure, String temperature, String feels_like, String humidity, String clouds, String cityName, String reqHour) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("numDay", numDay);
        contentValues.put("description", description);
        contentValues.put("speed", speed);
        contentValues.put("pressure", pressure);
        contentValues.put("temperature", temperature);
        contentValues.put("feels_like", feels_like);
        contentValues.put("humidity", humidity);
        contentValues.put("clouds", clouds);
        contentValues.put("cityName", cityName);
        contentValues.put("reqHour", reqHour);
        db.insert(TableName, null, contentValues);
        return true;
    }

    public ArrayList<String> getDataFromDataBase(String latitude, String longitude, String numDay) {
        ArrayList<String> exactWeatherInfo = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("select * from WeatherInfo", null);

        if (cursorCourses.moveToFirst()) {
            do {
                if (cursorCourses.getString(0).equals(latitude) &&
                        cursorCourses.getString(1).equals(longitude) &&
                        cursorCourses.getString(2).equals(numDay)) {
                    exactWeatherInfo.add(cursorCourses.getString(0));
                    exactWeatherInfo.add(cursorCourses.getString(1));
                    exactWeatherInfo.add(cursorCourses.getString(2));
                    exactWeatherInfo.add(cursorCourses.getString(3));
                    exactWeatherInfo.add(cursorCourses.getString(4));
                    exactWeatherInfo.add(cursorCourses.getString(5));
                    exactWeatherInfo.add(cursorCourses.getString(6));
                    exactWeatherInfo.add(cursorCourses.getString(7));
                    exactWeatherInfo.add(cursorCourses.getString(8));
                    exactWeatherInfo.add(cursorCourses.getString(9));
                    exactWeatherInfo.add(cursorCourses.getString(10));
                    exactWeatherInfo.add(cursorCourses.getString(11));
                    return exactWeatherInfo;
                }
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();

        return null;
    }



    public  boolean isCityInfoExistInDB(String cityName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TableName, null);

        if (cursorCourses.moveToFirst()) {
            do {
                if (cursorCourses.getString(0).equals(cityName)) {
                    return true;
                }
            } while (cursorCourses.moveToNext());
            return false;
        }
        cursorCourses.close();
        return false;
    }
}
