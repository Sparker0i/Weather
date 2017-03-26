package com.a5corp.weather.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

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

        Intent myIntent = new Intent(this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent,0);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (preferences.getNotifs())            //If notification enabled
            am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR,
                    AlarmManager.INTERVAL_HOUR,
                    pendingIntent);
        else {
            am.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
