package com.a5corp.weather.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.a5corp.weather.R;
import com.a5corp.weather.activity.GlobalActivity;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.internet.CheckConnection;
import com.a5corp.weather.internet.FetchWeather;
import com.a5corp.weather.preferences.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MyAlarmService extends Service
{
    Preferences preferences;
    NotificationManager mManager;
    FetchWeather wt;
    Notification myNotification;
    PendingIntent pendingIntent;
    Notification.Builder builder;
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
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this , WeatherActivity.class);

        pendingIntent = PendingIntent.getActivity(this, 1, intent1 , 0);
        getWeather();
    }

    public void getWeather() {
        wt = new FetchWeather(this);
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
        builder = new Notification.Builder(this);
        String ut;
        if (preferences.getUnits().equals("metric"))
            ut = "°C";
        else
            ut = "°F";
        builder.setAutoCancel(false);
        builder.setTicker(temp + ut + " at " + city);
        builder.setContentTitle("Weather Notification");
        builder.setContentText(temp + ut + " at " + city);
        builder.setStyle(new Notification.BigTextStyle().bigText("City : " + city
                + "\nTemperature : " + temp + ut
                + "\nPressure : " + pressure
                + "\nHumidity : " + humidity));
        builder.setSmallIcon(R.mipmap.ic_launcher_dark);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(false);
        myNotification = builder.build();

        if (preferences.getNotifs()) {
            mManager.notify(0, myNotification);
            Log.i("Built", "Notification");
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

}