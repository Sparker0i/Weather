package com.a5corp.weather.internet;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.a5corp.weather.R;
import com.a5corp.weather.model.Info;
import com.a5corp.weather.model.WeatherFort;
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

public class FetchWeather extends AsyncTask<String , Void , Info> {

    private static final String OPEN_WEATHER_MAP_FORECAST_API = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private static final String OPEN_WEATHER_MAP_DAILY_API = "http://api.openweathermap.org/data/2.5/weather?";
    private final String LOG_TAG = FetchWeather.class.getSimpleName();

    private Context context;
    private final String QUERY_PARAM = "q";
    private final String FORMAT_PARAM = "mode";
    private final String FORMAT_VALUE = "json";
    final String LAT_PARAM = "lat";
    final String LON_PARAM = "lon";
    private final String UNITS_PARAM = "units";
    private String UNITS_VALUE;
    private final String DAYS_PARAM = "cnt";
    private Uri builtDay , builtFort;
    private Preferences preferences;

    public FetchWeather(Context mContext) {
        context = mContext;
        preferences = new Preferences(context);
    }

    @Override
    public Info doInBackground(String... params) {
        if (params.length == 1)
            city(params);
        else
            coordinates(params);
        try {
            Log.d(LOG_TAG , "Execution");
            URL fort = new URL(builtFort.toString());
            Log.i("fort" , fort.toString());
            Log.d(LOG_TAG , "URI Ready");

            Info array = new Info();
            array.day = gsonWeather();
            array.fort = gsonFort();

            // This value will be 404 if the request was not successful
            if(array.day.getCod() != 200 | array.fort.getCod() != 200) {
                Log.e(LOG_TAG , "Execution Failed");
                return null;
            }

            return array;
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
                .appendQueryParameter("appId" , context.getString(R.string.open_weather_maps_app_id))
                .build();
        builtFort = Uri.parse(OPEN_WEATHER_MAP_FORECAST_API).buildUpon()
                .appendQueryParameter(QUERY_PARAM , params[0])
                .appendQueryParameter(FORMAT_PARAM , FORMAT_VALUE)
                .appendQueryParameter(UNITS_PARAM , UNITS_VALUE)
                .appendQueryParameter(DAYS_PARAM , Integer.toString(10))
                .build();
    }

    private void coordinates(String... params) {
        UNITS_VALUE = preferences.getUnits();
        builtDay = Uri.parse(OPEN_WEATHER_MAP_DAILY_API).buildUpon()
                .appendQueryParameter(LAT_PARAM , params[0])
                .appendQueryParameter(LON_PARAM , params[1])
                .appendQueryParameter(FORMAT_PARAM , FORMAT_VALUE)
                .appendQueryParameter(UNITS_PARAM , UNITS_VALUE)
                .appendQueryParameter(DAYS_PARAM , Integer.toString(10))
                .build();
        builtFort = Uri.parse(OPEN_WEATHER_MAP_FORECAST_API).buildUpon()
                .appendQueryParameter(LAT_PARAM , params[0])
                .appendQueryParameter(LON_PARAM , params[1])
                .appendQueryParameter(FORMAT_PARAM , FORMAT_VALUE)
                .appendQueryParameter(UNITS_PARAM , UNITS_VALUE)
                .appendQueryParameter(DAYS_PARAM , Integer.toString(10))
                .build();
    }

    private WeatherInfo gsonWeather() throws IOException {
        URL day = new URL(builtDay.toString());
        HttpURLConnection connection1 = (HttpURLConnection) day.openConnection();
        connection1.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));

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
            Log.e("FetchWeather", "Failed to parse JSON due to: " + ex);
        }
        return null;
    }

    private WeatherFort gsonFort() throws IOException{
        URL fort = new URL(builtFort.toString());
        HttpURLConnection connection1 = (HttpURLConnection) fort.openConnection();
        connection1.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));

        InputStream content = connection1.getInputStream();

        try {
            //Read the server response and attempt to parse it as JSON
            Reader reader = new InputStreamReader(content);

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            WeatherFort posts = gson.fromJson(reader, WeatherFort.class);
            System.out.println(gson.toJson(posts));
            content.close();
            return posts;
        } catch (Exception ex) {
            Log.e("FetchWeather", "Failed to parse JSON due to: " + ex);
        }
        return null;
    }
}
