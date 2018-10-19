package com.a5corp.weather.preferences;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.a5corp.weather.model.WeatherInfo;
import com.a5corp.weather.utils.Constants;

public class Prefs {
    private static SharedPreferences prefs;

    public Prefs(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public String getCity() {
        return prefs.getString(Constants.CITY, null);
    }

    public void setCity(String city) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.CITY, city);
        prefsEditor.apply();
    }

    public void setLaunched() {
        prefs.edit().putBoolean(Constants.FIRST, true).apply();
    }

    public boolean getLaunched() {
        return prefs.getBoolean(Constants.FIRST, false);
    }

    public void setLastCity(String city) {
        prefs.edit().putString(Constants.LASTCITY, city).apply();
    }

    public String getTime() {
        long time = Long.parseLong(prefs.getString(Constants.PREF_REFRESH_INTERVAL, Long.toString(AlarmManager.INTERVAL_HOUR / 60000))) * 60000;
        return Long.toString(time);
    }

    public String getLanguage() {
        return prefs.getString(Constants.PREF_DISPLAY_LANGUAGE, "en");
    }

    public String getLastCity() {
        return prefs.getString(Constants.LASTCITY, null);
    }

    public void setLatitude(float lat) {
        prefs.edit().putFloat(Constants.LATITUDE, lat).apply();
    }

    public float getLatitude() {
        return prefs.getFloat(Constants.LATITUDE, 0);
    }

    public void setLongitude(float lon) {
        prefs.edit().putFloat(Constants.LONGITUDE, lon).apply();
    }

    public float getLongitude() {
        return prefs.getFloat(Constants.LONGITUDE, 0);
    }

    public void setUnits(String string) {
        prefs.edit().putString(Constants.UNITS, string).apply();
    }

    public String getUnits() {
        return prefs.getString(Constants.UNITS, Constants.METRIC);
    }

    public void setNotifs(Boolean bool) {
        prefs.edit().putBoolean(Constants.NOTIFICATIONS, bool).apply();
    }

    public Boolean getNotifs() {
        return prefs.getBoolean(Constants.NOTIFICATIONS, false);
    }

    public void setv3TargetShown(boolean bool) {
        prefs.edit().putBoolean(Constants.V3TUTORIAL, bool).apply();
    }

    public boolean getv3TargetShown() {
        return prefs.getBoolean(Constants.V3TUTORIAL, false);
    }

    public void setWeatherKey(String str) {
        prefs.edit().putString(Constants.PREF_OWM_KEY, str).apply();
    }

    public String getWeatherKey() {
        return prefs.getString(Constants.PREF_OWM_KEY, Constants.OWM_APP_ID);
    }

    public void setTemperature(double temp) {
        prefs.edit().putLong(Constants.LARGE_WIDGET_TEMPERATURE, Double.doubleToLongBits(temp)).apply();
    }

    public double getTemperature() {
        return Double.longBitsToDouble(prefs.getLong(Constants.LARGE_WIDGET_TEMPERATURE, 0));
    }

    public void setPressure(double pressure) {
        prefs.edit().putLong(Constants.LARGE_WIDGET_PRESSURE, Double.doubleToLongBits(pressure)).apply();
    }

    public double getPressure() {
        return Double.longBitsToDouble(prefs.getLong(Constants.LARGE_WIDGET_PRESSURE, 0));
    }

    public void setHumidity(int humidity) {
        prefs.edit().putInt(Constants.LARGE_WIDGET_HUMIDITY, humidity).apply();
    }

    public int getHumidity() {
        return prefs.getInt(Constants.LARGE_WIDGET_HUMIDITY, 0);
    }

    public void setSpeed(float speed) {
        prefs.edit().putFloat(Constants.LARGE_WIDGET_WIND_SPEED, speed).apply();
    }

    public float getSpeed() {
        return prefs.getFloat(Constants.LARGE_WIDGET_WIND_SPEED, 0);
    }

    public void setIcon(int id) {
        prefs.edit().putInt(Constants.LARGE_WIDGET_ICON, id).apply();
    }

    public int getIcon() {
        return prefs.getInt(Constants.LARGE_WIDGET_ICON, 500);
    }

    public void setDescription(String description) {
        prefs.edit().putString(Constants.LARGE_WIDGET_DESCRIPTION, description).apply();
    }

    public String getDescription() {
        return prefs.getString(Constants.LARGE_WIDGET_DESCRIPTION, "Moderate Rain");
    }

    public void setCountry(String country) {
        prefs.edit().putString(Constants.LARGE_WIDGET_COUNTRY, country).apply();
    }

    public String getCountry() {
        return prefs.getString(Constants.LARGE_WIDGET_COUNTRY, "IN");
    }

    public void saveWeather(WeatherInfo weather) {
        setTemperature(weather.getMain().getTemp());
        setPressure(weather.getMain().getPressure());
        setHumidity(weather.getMain().getHumidity());
        setSpeed(weather.getWind().getSpeed());
        setIcon(weather.getWeather().get(0).getId());
        setCountry(weather.getSys().getCountry());
        setDescription(weather.getWeather().get(0).getDescription());
    }

    public boolean isTimeFormat24Hours() {
        return prefs.getBoolean(Constants.PREF_TIME_FORMAT, false);
    }
}
