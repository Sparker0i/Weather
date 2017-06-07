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

    public void setSunrise(long sunrise) {
        prefs.edit().putLong(Constants.LARGE_WIDGET_SUNRISE , sunrise).apply();
    }

    public long getSunrise() {
        return prefs.getLong(Constants.LARGE_WIDGET_SUNRISE , 0);
    }

    public void setDescription(String description) {
        prefs.edit().putString(Constants.LARGE_WIDGET_DESCRIPTION , description).apply();
    }

    public String getDescription() {
        return prefs.getString(Constants.LARGE_WIDGET_DESCRIPTION , "Cloudy");
    }

    public void setSunset(long sunset) {
        prefs.edit().putLong(Constants.LARGE_WIDGET_SUNSET , sunset).apply();
    }

    public long getSunset() {
        return prefs.getLong(Constants.LARGE_WIDGET_SUNSET , 0);
    }

    public void saveWeather(WeatherInfo weather) {
        setTemperature(weather.getMain().getTemp());
        setPressure(weather.getMain().getPressure());
        setHumidity(weather.getMain().getHumidity());
        setSpeed(weather.getWind().getSpeed());
        setIcon(weather.getWeather().get(0).getId());
        setSunrise(weather.getSys().getSunrise());
        setDescription(weather.getWeather().get(0).getDescription());
        setSunset(weather.getSys().getSunset());
    }
}
