package com.a5corp.weather.utilities;

public class Constants {
    public static final String WEATHER_DAILY = "http://api.openweathermap.org/data/2.5/forecast/daily";

    public static final String QUERY_PARAM = "q";
    public static final String FORMAT_PARAM = "mode";
    public static final String FORMAT_VALUE = "json";
    public static final String LAT_PARAM = "lat";
    public static final String LON_PARAM = "lon";
    public static final String UNITS_PARAM = "units";
    public static final String UNITS_VALUE = "metric";
    public static final String DAYS_PARAM = "cnt";

    public static final int PARSE_RESULT_SUCCESS = 0;
    public static final int TASK_RESULT_ERROR = -1;
    public static final int PARSE_RESULT_ERROR = -2;
}
