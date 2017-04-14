package com.a5corp.weather.internet;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.a5corp.weather.model.WeatherInfo;
import com.a5corp.weather.preferences.Preferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchWeatherOther extends AsyncTask<String , Void , WeatherInfo> {
    private static final String OPEN_WEATHER_MAP_DAILY_API = "http://api.openweathermap.org/data/2.5/weather?";
    private final String LOG_TAG = FetchWeatherOther.class.getSimpleName();

    private Context context;
    private final String QUERY_PARAM = "q";
    private final String FORMAT_PARAM = "mode";
    private final String FORMAT_VALUE = "json";
    private final String UNITS_PARAM = "units";
    private String UNITS_VALUE;
    private final String DAYS_PARAM = "cnt";
    private Uri builtDay;
    private Preferences preferences;

    public FetchWeatherOther(Context mContext) {
        context = mContext;
        preferences = new Preferences(context);
    }

    @Override
    public WeatherInfo doInBackground(String... params) {
        city(params);
        try {
            Log.d(LOG_TAG , "Execution");
            URL day = new URL(builtDay.toString());
            Log.i("day" , day.toString());
            Log.d(LOG_TAG , "URI Ready");

            WeatherInfo weather = gsonWeather();
            if (weather.getCod() != 200)
                return null;
            return weather;
        }
        catch(IOException e){
            Log.e(LOG_TAG , "Execution Failed IO");
            e.printStackTrace();
            return null;
        }
    }

    private void city(String... params) {
        UNITS_VALUE = preferences.getUnits();
        System.out.println(UNITS_VALUE);
        builtDay = Uri.parse(OPEN_WEATHER_MAP_DAILY_API).buildUpon()
                .appendQueryParameter(QUERY_PARAM , params[0])
                .appendQueryParameter(FORMAT_PARAM , FORMAT_VALUE)
                .appendQueryParameter(UNITS_PARAM , UNITS_VALUE)
                .appendQueryParameter(DAYS_PARAM , Integer.toString(10))
                .build();
    }

    private WeatherInfo gsonWeather() throws IOException {
        URL day = new URL(builtDay.toString());
        HttpURLConnection connection1 = (HttpURLConnection) day.openConnection();
        connection1.addRequestProperty("x-api-key", preferences.getWeatherKey());

        InputStream content = connection1.getInputStream();

        try {
            //Read the server response and attempt to parse it as JSON
            Reader reader = new InputStreamReader(content);

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            WeatherInfo posts = gson.fromJson(reader, WeatherInfo.class);
            System.out.println(gson.toJson(posts));
            content.close();
            return posts;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Failed to parse JSON due to: " + ex);
        }
        return null;
    }
}
