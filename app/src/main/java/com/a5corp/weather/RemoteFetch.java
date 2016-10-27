package com.a5corp.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import org.json.JSONObject;

import android.content.Context;
//import android.location.Location;

class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_FORECAST_API = "http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=metric&cnt=10";
    private static final String OPEN_WEATHER_MAP_DAILY_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    //private Location mLastLocation;

    static JSONObject[] getJSON(Context context, String city){
        //double Latitude , Longitude;
        try {
            URL day = new URL(String.format(OPEN_WEATHER_MAP_FORECAST_API, city));
            URL fort = new URL(String.format(OPEN_WEATHER_MAP_DAILY_API, city));

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
            //double latitude = data1.getJSONObject("coord").getDouble("lat") , longitude = data1.getJSONObject("coord").getDouble("lon");


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

    static JSONObject[] getJSONLocation(Context context, double longitude, double latitude){
        //double Latitude , Longitude;
        try {
            final String OPEN_WEATHER_MAP_FORECAST_API = "http://api.openweathermap.org/data/2.5/forecast/daily?q=%f,%f&units=metric&cnt=10";
            final String OPEN_WEATHER_MAP_DAILY_API = "http://api.openweathermap.org/data/2.5/weather?q=%f,%f&units=metric";
            URL day = new URL(String.format(Locale.ENGLISH, OPEN_WEATHER_MAP_FORECAST_API, latitude, longitude));
            URL fort = new URL(String.format(Locale.ENGLISH, OPEN_WEATHER_MAP_DAILY_API, latitude, longitude));

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
            //double latitude = data1.getJSONObject("coord").getDouble("lat") , longitude = data1.getJSONObject("coord").getDouble("lon");


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