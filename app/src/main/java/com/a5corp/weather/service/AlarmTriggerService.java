package com.a5corp.weather.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.receiver.MyReceiver;

public class AlarmTriggerService extends IntentService {

    private static final String TAG = "AlarmTriggerService";
    PendingIntent pendingIntent;
    Prefs preferences;

    public AlarmTriggerService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        preferences = new Prefs(this);

        Intent myIntent = new Intent(this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (preferences.getNotifs())
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    SystemClock.elapsedRealtime() + 2,
                    AlarmManager.INTERVAL_HOUR,
                    pendingIntent);
    }
}
