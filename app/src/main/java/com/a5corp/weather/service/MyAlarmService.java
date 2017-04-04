package com.a5corp.weather.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
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
import com.a5corp.weather.preferences.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyAlarmService extends Service
{
    Preferences preferences;
    NotificationManagerCompat mManager;
    FetchWeatherOther wt;
    Notification myNotification;
    PendingIntent pendingIntent;
    NotificationCompat.Builder builder;
    JSONObject json;

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        preferences = new Preferences(this);
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
            Log.i("Cannot Build" , "Notification");
        }
        return Service.START_NOT_STICKY;
    }

    public void getWeather() {
        wt = new FetchWeatherOther(this);
        new Thread() {
            public void run() {
                try {
                    json = wt.execute(preferences.getLastCity()).get()[0];
                }
                catch (InterruptedException iex) {
                    Log.e("InterruptedException" , "iex");
                }
                catch (ExecutionException eex) {
                    Log.e("ExecutionException" , "eex");
                }
                getObjects();
            }
        }.start();
    }

    public void getObjects() {
        try {
            double temp = json.getJSONObject("main").getDouble("temp");
            String city = json.getString("name");
            double pressure = json.getJSONObject("main").getDouble("pressure");
            double humidity = json.getJSONObject("main").getDouble("humidity");
            buildNotification(temp , pressure , humidity , city);
        }
        catch (JSONException jex) {
            jex.printStackTrace();
        }
    }

    public void buildNotification(double temp , double pressure , double humidity , String city) {
        builder = new NotificationCompat.Builder(this);
        String ut;
        if (preferences.getUnits().equals("metric"))
            ut = "°C";
        else
            ut = "°F";
        builder.setAutoCancel(true);
        builder.setTicker(Math.round(temp) + ut + " at " + city);
        builder.setContentTitle("Weather Notification");
        builder.setContentText(Math.round(temp) + ut + " at " + city);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("City : " + city
                + "\nTemperature : " + Math.round(temp) + ut
                + "\nPressure : " + pressure + " hPa"
                + "\nHumidity : " + humidity + "%"));
        builder.setSmallIcon(R.drawable.ic_notification_icon);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder.setColor(Color.parseColor("#ff0000"));
        myNotification = builder.build();
        mManager.notify(0, myNotification);
        Log.i("Built", "Notification");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

}