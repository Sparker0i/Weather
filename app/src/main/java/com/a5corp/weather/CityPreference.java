package com.a5corp.weather;

import android.app.Activity;
import android.content.SharedPreferences;

public class CityPreference {

    public static SharedPreferences prefs;

    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }
    // If the user has not chosen a city yet, return
    // Sydney as the default city
    String getCity(){
        return prefs.getString("city", "Sydney");
    }

    void setCity(String city) {
        prefs.edit().putString("city", city).commit();
    }

    void setLaunched() { prefs.edit().putBoolean("first" , false).commit();}
}
