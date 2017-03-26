package com.a5corp.weather.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.internet.FetchWeather;
import com.a5corp.weather.preferences.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyAlarmService extends Service
{
    Preferences preferences;
    NotificationManagerCompat mManager;
    FetchWeather wt;
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
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        mManager = NotificationManagerCompat.from(this);
        Intent intent1 = new Intent(this , WeatherActivity.class);

        pendingIntent = PendingIntent.getActivity(this, 1, intent1 , 0);
        if (preferences.getNotifs() && !checkApp()) {
            getWeather();
        }
        else {
            Log.i("Cannot Build" , "Notification");
        }
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
        builder = new NotificationCompat.Builder(this);
        String ut;
        if (preferences.getUnits().equals("metric"))
            ut = "°C";
        else
            ut = "°F";
        builder.setAutoCancel(true);
        builder.setTicker(temp + ut + " at " + city);
        builder.setContentTitle("Weather Notification");
        builder.setContentText(temp + ut + " at " + city);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("City : " + city
                + "\nTemperature : " + temp + ut
                + "\nPressure : " + pressure + " hPa"
                + "\nHumidity : " + humidity + "%"));
        builder.setSmallIcon(R.drawable.ic_notification_icon);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(false);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder.setColor(Color.YELLOW);
        myNotification = builder.build();
        mManager.notify(0, myNotification);
        Log.i("Built", "Notification");
    }

    public boolean checkApp(){
        ActivityManager am = (ActivityManager) this
                .getSystemService(ACTIVITY_SERVICE);

        // get the info from the currently running task
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return componentInfo.getPackageName().equalsIgnoreCase("com.a5corp.weather");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

}