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
    private static final int DB_VERSION = 2;
    public static final String TableName = "WeatherInfo";
    public static final String city = "city";
    public static final String description = "description";
    public static final String temperature = "temperature";
    public static final String feels_like = "feels_like";
    public static final String speed = "speed";
    public static final String pressure = "pressure";
    public static final String humidity = "humidity";
    public static final String clouds = "clouds";

    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table WeatherInfo(city text primary key, description text, speed text, pressure text, temperature text, feels_liks text, humidity text, clouds text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists WeatherInfo");
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String city, String description, String speed, String pressure, String temperature, String feels_like, String humidity, String clouds) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("city", city);
        contentValues.put("description", description);
        contentValues.put("speed", speed);
        contentValues.put("pressure", pressure);
        contentValues.put("temperature", temperature);
        contentValues.put("feels_like", feels_like);
        contentValues.put("humidity", humidity);
        contentValues.put("clouds", clouds);
        db.insert(TableName, null, contentValues);
        db.close();
        return true;
    }

    public ArrayList<String> getDataByCityName(String cityName) {
        ArrayList<String> exactWeatherInfo = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TableName, null);

        if (cursorCourses.moveToFirst()) {
            do {
                if (cursorCourses.getString(0).equals(cityName)) {
                    exactWeatherInfo.add(cursorCourses.getString(0));
                    exactWeatherInfo.add(cursorCourses.getString(1));
                    exactWeatherInfo.add(cursorCourses.getString(2));
                    exactWeatherInfo.add(cursorCourses.getString(3));
                    exactWeatherInfo.add(cursorCourses.getString(4));
                    exactWeatherInfo.add(cursorCourses.getString(5));
                    exactWeatherInfo.add(cursorCourses.getString(6));
                    exactWeatherInfo.add(cursorCourses.getString(7));
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
