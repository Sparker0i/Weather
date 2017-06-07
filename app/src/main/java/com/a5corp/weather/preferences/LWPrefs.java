package com.a5corp.weather.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.a5corp.weather.model.WeatherInfo;
import com.a5corp.weather.utils.Constants;

public class LWPrefs {
    private static SharedPreferences prefs;
    private Context context;

    public LWPrefs(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public String getCity() {
        return new Prefs(context).getCity();
    }

    public void setCity(String city) {
        new Prefs(context).setCity(city);
    }

    public String getUnits() {
        return prefs.getString(Constants.UNITS , "metric");
    }

    public void setTemperature(double temp) {
        prefs.edit().putLong(Constants.LARGE_WIDGET_TEMPERATURE , Double.doubleToLongBits(temp)).apply();
    }

    public double getTemperature() {
        return Double.longBitsToDouble(prefs.getLong(Constants.LARGE_WIDGET_TEMPERATURE , 0));
    }

    public void setPressure(double pressure) {
        prefs.edit().putLong(Constants.LARGE_WIDGET_PRESSURE , Double.doubleToLongBits(pressure)).apply();
    }

    public double getPressure() {
        return Double.longBitsToDouble(prefs.getLong(Constants.LARGE_WIDGET_PRESSURE , 0));
    }

    public void setHumidity(int humidity) {
        prefs.edit().putInt(Constants.LARGE_WIDGET_HUMIDITY , humidity).apply();
    }

    public int getHumidity() {
        return prefs.getInt(Constants.LARGE_WIDGET_HUMIDITY , 0);
    }

    public void setSpeed(float speed) {
        prefs.edit().putFloat(Constants.LARGE_WIDGET_WIND_SPEED , speed).apply();
    }

    public float getSpeed() {
        return prefs.getFloat(Constants.LARGE_WIDGET_WIND_SPEED , 0);
    }

    public void setIcon(int id) {
        prefs.edit().putInt(Constants.LARGE_WIDGET_ICON , id).apply();
    }

    public int getIcon() {
        return prefs.getInt(Constants.LARGE_WIDGET_ICON , 500);
    }

    public void setDescription(String description) {
        prefs.edit().putString(Constants.LARGE_WIDGET_DESCRIPTION , description).apply();
    }

    public String getDescription() {
        return prefs.getString(Constants.LARGE_WIDGET_DESCRIPTION , "Moderate Rain");
    }

    public void setCountry(String country) {
        prefs.edit().putString(Constants.LARGE_WIDGET_COUNTRY , country).apply();
    }

    public String getCountry() {
        return prefs.getString(Constants.LARGE_WIDGET_COUNTRY , "IN");
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
}
