package com.a5corp.weather.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.preferences.Preferences;

public class MyAlarmService extends Service
{
    Preferences preferences;
    NotificationManager mManager;

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

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent1 , 0);

        Notification myNotification;
        Notification.Builder builder = new Notification.Builder(this);
        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("WhatsApp Notification");
        builder.setContentText("You have a new message");
        builder.setSmallIcon(R.mipmap.ic_launcher_dark);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(false);
        builder.build();
        myNotification = builder.build();
        if (preferences.getNotifs())
            mManager.notify(0, myNotification);
        Log.i("Built" , "Notification");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

}