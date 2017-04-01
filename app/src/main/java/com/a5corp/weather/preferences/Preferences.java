package com.a5corp.weather.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Preferences {
    private static SharedPreferences prefs;

    public Preferences(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public String getCity() {
        return prefs.getString("city", null);
    }

    public void setCity(String city) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("city", city);
        prefsEditor.apply();
    }

    public void setLaunched() {
        prefs.edit().putBoolean("first" , false).apply();
    }

    public boolean getLaunched() {
        return prefs.getBoolean("first" , true);
    }

    public void setLastCity(String city) {
        prefs.edit().putString("lcity" , city).apply();
    }

    public String getLastCity() {
        return prefs.getString("lcity" , null);
    }

    public void setLatitude(float lat) {
        prefs.edit().putFloat("lat" , lat).apply();
    }

    public float getLatitude() {
        return prefs.getFloat("lat" , 0);
    }

    public void setLongitude(float lon) {
        prefs.edit().putFloat("lon" , lon).apply();
    }

    public float getLongitude() {
        return prefs.getFloat("lon" , 0);
    }

    public void setUnits(String string) {
        prefs.edit().putString("units" , string).apply();
        Log.i("units" , string);
    }

    public String getUnits() {
        return prefs.getString("units" , "metric");
    }

    public void setNotifs(Boolean bool) {
        prefs.edit().putBoolean("notif" , bool).apply();
    }

    public Boolean getNotifs() {
        return prefs.getBoolean("notif" , false);
    }

    public void storeSmallWidget(String json) {
        prefs.edit().putString("jsonsmall" , json).apply();
    }

    public String getSmallWidget() {
        return prefs.getString("jsonsmall" , null);
    }

    public void storeLargeWidget(String json) {
        prefs.edit().putString("jsonlarge" , json).apply();
    }

    public String getLargeWidget() {
        return prefs.getString("jsonlarge" , null);
    }

    public void setv3TargetShown(boolean bool) {
        prefs.edit().putBoolean("search-v3-shown" , bool).apply();
    }

    public boolean getv3TargetShown() {
        return prefs.getBoolean("search-v3-shown" , false);
    }
}
