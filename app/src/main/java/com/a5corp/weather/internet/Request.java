package com.a5corp.weather.internet;

import android.content.Context;
import com.a5corp.weather.model.Log;

import com.a5corp.weather.model.WeatherInfo;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Constants;
import com.a5corp.weather.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request
{

    public Context context;

    public Request(Context mContext) {
        this.context = mContext;
    }

    private WeatherInfo gsonWeather(URL url) throws IOException {
        HttpURLConnection connection1 = (HttpURLConnection) url.openConnection();
        connection1.addRequestProperty("x-api-key", new Prefs(context).getWeatherKey());

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

    public WeatherInfo getItems(String city, String units) throws IOException
    {
        return gsonWeather(Utils.getWeatherForecastUrl(Constants.OPEN_WEATHER_MAP_DAILY_API , city, units));
    }
}
