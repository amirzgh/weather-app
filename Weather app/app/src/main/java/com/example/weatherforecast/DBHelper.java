package com.example.weatherforecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "Weather Database";

    public static final String TableName = "WeatherInfo";
    public static final String city = "city";
    public static final String description = "description";
    public static final String temp = "temp";
    public static final String feels_like = "feels_like";
    public static final String speed = "speed";
    public static final String pressure = "pressure";
    public static final String humidity = "humidity";
    public static final String all = "all";


    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table WeatherInfo(city text primary key, description text, speed text, pressure text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists WeatherInfo");
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String city, String description, String speed, String pressure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("city", city);
        contentValues.put("description", description);
        contentValues.put("speed", speed);
        contentValues.put("pressure", pressure);
        db.insert(TableName, null, contentValues);

        return true;
    }
}
