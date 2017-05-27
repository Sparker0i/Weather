package com.a5corp.weather.internet;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.a5corp.weather.model.WeatherInfo;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchWeatherOther extends AsyncTask<String , Void , WeatherInfo> {
    private final String LOG_TAG = FetchWeatherOther.class.getSimpleName();

    private Uri builtDay;
    private Prefs preferences;

    public FetchWeatherOther(Context context) {
        preferences = new Prefs(context);
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
            if (weather.getCod() != 200) {
                Log.e(LOG_TAG , "cod is not 200");
                return null;
            }
            return weather;
        }
        catch(IOException e){
            Log.e(LOG_TAG , "Execution Failed IO");
            e.printStackTrace();
            return null;
        }
    }

    private void city(String... params) {
        String UNITS_VALUE = preferences.getUnits();
        System.out.println(UNITS_VALUE);
        builtDay = Uri.parse(Constants.OPEN_WEATHER_MAP_DAILY_API).buildUpon()
                .appendQueryParameter(Constants.QUERY_PARAM , params[0])
                .appendQueryParameter(Constants.FORMAT_PARAM , Constants.FORMAT_VALUE)
                .appendQueryParameter(Constants.UNITS_PARAM , UNITS_VALUE)
                .appendQueryParameter(Constants.DAYS_PARAM , Integer.toString(10))
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
