package com.a5corp.weather.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.a5corp.weather.preferences.Preferences;
import com.a5corp.weather.receiver.MyReceiver;

import java.util.Calendar;

public class CurrentWeatherService extends IntentService {

    private static final String TAG = "CurrentWeatherService";
    PendingIntent pendingIntent;
    Preferences preferences;

    public CurrentWeatherService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        preferences = new Preferences(this);
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, 6);
        calendar.set(Calendar.YEAR, 2013);
        calendar.set(Calendar.DAY_OF_MONTH, 13);

        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 48);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.PM);

        Intent myIntent = new Intent(this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent,0);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        long recurring = (1 * 30000);  // in milliseconds
        if (preferences.getNotifs())
            am.setRepeating(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis(), recurring, pendingIntent);
    }
}
