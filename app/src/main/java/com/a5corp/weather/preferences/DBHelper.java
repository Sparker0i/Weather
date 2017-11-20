package com.a5corp.weather.preferences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WeatherDatabase.db";
    private static final String TABLE_CITIES = "cities";

    private static final String KEY_CITY = "city";
    private static final String KEY_ID = "id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance  
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_CITIES + " (" +
                KEY_CITY + " TEXT," +
                KEY_ID + " INTEGER PRIMARY KEY" + ");";
        db.execSQL(CREATE_QUESTION_TABLE);
    }

    // Upgrading database  
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed  
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);

        // Create tables again  
        onCreate(db);
    }

    public void addCity(String string) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CITY, string);

        // Inserting Row
        db.insert(TABLE_CITIES, null, values);
        //2nd argument is String containing nullColumnHack

        db.close(); // Closing database connection  
    }

    public List<String> getCities() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CITIES + " ORDER BY " + KEY_ID + " DESC;", null);
        List<String> categoryList = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                do {
                    categoryList.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
        return categoryList;
    }

    public boolean cityExists(String string) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_CITY + " FROM " + TABLE_CITIES + ";", null);

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(0).equals(string))
                        return true;
                }
                while (cursor.moveToNext());
            }
        }
        cursor.close();
        return false;
    }

    public void deleteCity(String string) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CITIES + " WHERE " + KEY_CITY + " LIKE \'" + string + "\'");
    }
}
