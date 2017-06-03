package com.a5corp.weather.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.internet.FetchWeatherOther;
import com.a5corp.weather.model.WeatherInfo;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Constants;

import java.util.concurrent.ExecutionException;

public class NotificationBuilderService extends Service
{
    Prefs preferences;
    NotificationManagerCompat mManager;
    FetchWeatherOther wt;
    Notification myNotification;
    PendingIntent pendingIntent;
    NotificationCompat.Builder builder;
    WeatherInfo json;
    String data;

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        preferences = new Prefs(this);
    }

    @SuppressWarnings("static-access")
    @Override
    public int onStartCommand(Intent intent, int flag, int startId)
    {
        super.onStartCommand(intent, 0, startId);

        mManager = NotificationManagerCompat.from(this);
        Intent intent1 = new Intent(this , WeatherActivity.class);

        pendingIntent = PendingIntent.getActivity(this, 1, intent1 , 0);
        if (preferences.getNotifs()) {
            getWeather();
        }
        else {
            mManager.cancelAll();
            Log.i("Cannot Build" , "Notification");
        }
        return Service.START_NOT_STICKY;
    }

    public void getWeather() {
        wt = new FetchWeatherOther(this);
        new Thread() {
            public void run() {
                try {
                    json = wt.execute(preferences.getLastCity()).get();
                }
                catch (InterruptedException iex) {
                    Log.e("InterruptedException" , "iex");
                    return;
                }
                catch (ExecutionException eex) {
                    Log.e("ExecutionException" , "eex");
                    return;
                }
                buildNotification();
            }
        }.start();
    }

    public void buildNotification() {
        double temp;
        String ut;
        String city;
        if (preferences.getUnits().equals("metric"))
            ut = "°C";
        else
            ut = "°F";
        try {
            city = json.getName();
            data += "City : " + city;
        }
        catch (Exception ex) {
            Log.e("Failed Notification" , "No City");
        }
        try {
            temp = json.getMain().getTemp();
            data += "\nTemperature : " + Math.round(temp) + ut;
        }
        catch (Exception ex) {
            Log.e("Failed Notification" , "No temp");
        }
        double pressure;
        try {
            pressure = json.getMain().getPressure();
            data += "\nPressure : " + pressure + " hPa";
        }
        catch (Exception ex) {
            Log.e("Failed Notification" , "No Pressure");
        }
        double humidity;
        try {
            humidity = json.getMain().getHumidity();
            data += "\nHumidity : " + humidity + "%";
        }
        catch (Exception ex) {
            Log.e("Failed Notification" , "No Humidity");
        }
        builder = new NotificationCompat.Builder(this);
        if (!data.equals("")) {
            mManager.cancelAll();
            builder.setAutoCancel(false);
            builder.setContentTitle("Weather Notification");
            builder.setContentText(data);
            builder.setSmallIcon(R.drawable.ic_notification_icon);
            builder.setContentIntent(pendingIntent);
            if (Build.VERSION.SDK_INT >= 24)
                builder.setColor(Color.parseColor("#ff0000"));
            myNotification = builder.build();
            mManager.notify(Constants.MY_NOTIFICATION_ID, myNotification);
            Log.i("Built", "Notification");
        }
    }
}