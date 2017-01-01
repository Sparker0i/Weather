package com.a5corp.weather;

import android.app.Activity;
import android.content.SharedPreferences;

class Preferences {
    private static SharedPreferences prefs;

    Preferences(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

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

    void setLastCity(String city) {
        prefs.edit().putString("lcity" , city).apply();
    }

    String getLastCity() {
        return prefs.getString("lcity" , "Sydney");
    }
}
