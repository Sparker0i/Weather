package com.a5corp.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.content.Context;

public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=metric&cnt=10";

    private static final String OPEN_WEATHER_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";

    public static JSONObject[] getJSON(Context context, String city){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            URL url1 = new URL(String.format(OPEN_WEATHER_API, city));

            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();
            HttpURLConnection connection1 =
                    (HttpURLConnection)url1.openConnection();

            connection.addRequestProperty("x-api-key",
                    context.getString(R.string.open_weather_maps_app_id));
            connection1.addRequestProperty("x-api-key" ,
                    context.getString(R.string.open_weather_maps_app_id));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())) , reader1 = new BufferedReader(
                    new InputStreamReader(connection1.getInputStream())
            );

            StringBuffer json = new StringBuffer(1024) , json1 = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
            String tmp1="";
            while((tmp1=reader1.readLine())!=null)
                json1.append(tmp1).append("\n");
            reader1.close();

            JSONObject data = new JSONObject(json.toString()) , data1 = new JSONObject(json1.toString());

            // This value will be 404 if the request was not
            // successful
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