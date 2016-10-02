package com.a5corp.weather;

import android.app.Activity;
import android.content.SharedPreferences;

class CityPreference {

    private static SharedPreferences prefs;

    CityPreference(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }
    // If the user has not chosen a city yet, return Sydney as the default city

    String getCity() {
        return prefs.getString("city", "Sydney");
    }

    boolean getLaunched() {
        return prefs.getBoolean("first" , true);
    }

    SharedPreferences getPrefs() {
        return prefs;
    }

    void setCity(String city) {
        prefs.edit().putString("city", city).apply();
    }

    void setLaunched() {
        prefs.edit().putBoolean("first" , false).apply();
    }
}
