package com.a5corp.weather.model;

import android.app.Application;
import android.app.Fragment;
import android.content.Intent;

import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.internet.FetchWeather;

import java.util.concurrent.ExecutionException;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Global extends Application {

    public Info info;
    public String latitude , longitude;

    public void init() throws InterruptedException , ExecutionException{
        info = new FetchWeather(getApplicationContext()).execute(latitude , longitude).get();
        Intent intent = new Intent(Global.this, WeatherActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
