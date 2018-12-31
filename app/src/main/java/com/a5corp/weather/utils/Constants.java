package com.a5corp.weather.utils;

import com.a5corp.weather.model.Info;

public class Constants {
    public static final String QUERY_PARAM = "q";
    public static final String SPLASH_DATA="splash_data";

    public static final String FORMAT_PARAM = "mode";
    public static final String FORMAT_VALUE = "json";
    public static final String UNITS_PARAM = "units";
    public static final String DAYS_PARAM = "cnt";
    
    public static final String CITY = "city";
    public static final String LASTCITY = "lcity";

    public static final String FIRST = "first";

    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lon";

    public static final String UNITS = "units";
    public static final String METRIC = "metric";
    public static final String IMPERIAL = "imperial";

    public static final String NOTIFICATIONS = "notifs";

    public static final String V3TUTORIAL = "tut-v3-shown";

    public static final String MAIL = "aadityamenon007@gmail.com";

    public static final int READ_COARSE_LOCATION = 20;
    public static final int WRITE_EXTERNAL_STORAGE = 21;

    public static final String OWM_APP_ID = "4c08a22b02c58467e6241629c1d08717";

    public static final String DESCRIBABLE_KEY = "describable_key";

    public static final String LIBRARY_ID = "libId";
    public static final String MODE = "mode";

    public static final String OPEN_WEATHER_MAP_FORECAST_API = "https://api.openweathermap.org/data/2.5/forecast/daily?";
    public static final String OPEN_WEATHER_MAP_DAILY_API = "https://api.openweathermap.org/data/2.5/weather?";

    public static final String LARGE_WIDGET_TEMPERATURE = "temperature";
    public static final String LARGE_WIDGET_DESCRIPTION = "description";
    public static final String LARGE_WIDGET_COUNTRY = "country";
    public static final String LARGE_WIDGET_PRESSURE = "pressure";
    public static final String LARGE_WIDGET_HUMIDITY = "humidity";
    public static final String LARGE_WIDGET_WIND_SPEED = "wind_speed";
    public static final String LARGE_WIDGET_ICON = "icon";

    //SETTINGS CONSTANTS
    public static final String PREF_TEMPERATURE_UNITS = "units";
    public static final String PREF_ENABLE_NOTIFS = "notifs";
    public static final String PREF_DISPLAY_LANGUAGE = "pref_language";
    public static final String PREF_OWM_KEY = "owm_key";
    public static final String PREF_DELETE_CITIES = "pref_delete_cities";
    public static final String PREF_REFRESH_INTERVAL = "pref_refresh_interval";
    public static final String PREF_TIME_FORMAT = "pref_time_format";
}
