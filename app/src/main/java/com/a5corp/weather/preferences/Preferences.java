package com.a5corp.weather.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static SharedPreferences prefs;
    private Context context;
    private final String USER_PREFS = "Prefs";

    public Preferences(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(USER_PREFS , Context.MODE_PRIVATE);
    }

    public String getCity() {
        return prefs.getString("city", null);
    }

    public boolean getLaunched() {
        return prefs.getBoolean("first" , true);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public void setCity(String city) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("city", city);
        prefsEditor.apply();
    }

    public void setLaunched() {
        prefs.edit().putBoolean("first" , false).apply();
    }

    public void setLastCity(String city) {
        prefs.edit().putString("lcity" , city).apply();
    }

    public String getLastCity() {
        return prefs.getString("lcity" , "Sydney");
    }
}
