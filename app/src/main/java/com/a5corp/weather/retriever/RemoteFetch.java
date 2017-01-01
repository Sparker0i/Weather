package com.a5corp.weather.retriever;

import android.content.Context;
import android.net.Uri;

import com.a5corp.weather.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_FORECAST_API = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private static final String OPEN_WEATHER_MAP_DAILY_API = "http://api.openweathermap.org/data/2.5/weather?";

    public static JSONObject[] getJSON(Context context, String city) {
        try {
            final String QUERY_PARAM = "q";
            final String FORMAT_PARAM = "mode";
            final String FORMAT_VALUE = "json";
            final String UNITS_PARAM = "units";
            final String UNITS_VALUE = "metric";
            final String DAYS_PARAM = "cnt";
            final int DAYS_VALUE = 10;

            Uri builtDay = Uri.parse(OPEN_WEATHER_MAP_DAILY_API).buildUpon()
                    .appendQueryParameter(QUERY_PARAM , city)
                    .appendQueryParameter(FORMAT_PARAM , FORMAT_VALUE)
                    .appendQueryParameter(UNITS_PARAM , UNITS_VALUE)
                    .appendQueryParameter(DAYS_PARAM , Integer.toString(DAYS_VALUE))
                    .build();
            URL day = new URL(builtDay.toString());

            Uri builtFort = Uri.parse(OPEN_WEATHER_MAP_FORECAST_API).buildUpon()
                    .appendQueryParameter(QUERY_PARAM , city)
                    .appendQueryParameter(FORMAT_PARAM , FORMAT_VALUE)
                    .appendQueryParameter(UNITS_PARAM , UNITS_VALUE)
                    .appendQueryParameter(DAYS_PARAM , Integer.toString(DAYS_VALUE))
                    .build();
            URL fort = new URL(builtFort.toString());

            HttpURLConnection connection0 = (HttpURLConnection)day.openConnection();
            HttpURLConnection connection1 = (HttpURLConnection)fort.openConnection();
            connection0.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));
            connection1.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));
            BufferedReader reader;
            StringBuilder json = new StringBuilder(1024) , json1 = new StringBuilder(1024);

            reader= new BufferedReader(new InputStreamReader(connection0.getInputStream()));
            String tmp;
            while((tmp = reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            reader = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
            while((tmp = reader.readLine())!=null)
                json1.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString()) , data1 = new JSONObject(json1.toString());

            // This value will be 404 if the request was not successful
            if(data.getInt("cod") != 200 || data1.getInt("cod") != 200){
                return null;
            }

            JSONObject array[] = new JSONObject[2];
            array[0] = data;
            array[1] = data1;
            return array;
        }catch(Exception e){
            return null;
        }
    }

    public static JSONObject[] getJSONLocation(Context context, String latitude, String longitude) {
        try {
            final String LAT_PARAM = "lat";
            final String LON_PARAM = "lon";
            final String FORMAT_PARAM = "mode";
            final String FORMAT_VALUE = "json";
            final String UNITS_PARAM = "units";
            final String UNITS_VALUE = "metric";
            final String DAYS_PARAM = "cnt";
            final int DAYS_VALUE = 10;

            Uri builtDay = Uri.parse(OPEN_WEATHER_MAP_DAILY_API).buildUpon()
                    .appendQueryParameter(LAT_PARAM , latitude)
                    .appendQueryParameter(LON_PARAM , longitude)
                    .appendQueryParameter(FORMAT_PARAM , FORMAT_VALUE)
                    .appendQueryParameter(UNITS_PARAM , UNITS_VALUE)
                    .appendQueryParameter(DAYS_PARAM , Integer.toString(DAYS_VALUE))
                    .build();
            URL day = new URL(builtDay.toString());

            Uri builtFort = Uri.parse(OPEN_WEATHER_MAP_FORECAST_API).buildUpon()
                    .appendQueryParameter(LAT_PARAM , latitude)
                    .appendQueryParameter(LON_PARAM , longitude)
                    .appendQueryParameter(FORMAT_PARAM , FORMAT_VALUE)
                    .appendQueryParameter(UNITS_PARAM , UNITS_VALUE)
                    .appendQueryParameter(DAYS_PARAM , Integer.toString(DAYS_VALUE))
                    .build();
            URL fort = new URL(builtFort.toString());

            HttpURLConnection connection0 = (HttpURLConnection)day.openConnection();
            HttpURLConnection connection1 = (HttpURLConnection)fort.openConnection();
            connection0.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));
            connection1.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));

            BufferedReader reader;
            StringBuilder json = new StringBuilder(1024) , json1 = new StringBuilder(1024);

            reader= new BufferedReader(new InputStreamReader(connection0.getInputStream()));
            String tmp;
            while((tmp = reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            reader = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
            while((tmp = reader.readLine())!=null)
                json1.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString()) , data1 = new JSONObject(json1.toString());

            // This value will be 404 if the request was not successful
            if(data.getInt("cod") != 200 || data1.getInt("cod") != 200){
                return null;
            }

            JSONObject array[] = new JSONObject[2];
            array[0] = data;
            array[1] = data1;
            return array;
        }catch(Exception e){
            return null;
        }
    }

}