package com.a5corp.weather.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.a5corp.weather.preferences.Preferences;
import com.a5corp.weather.receiver.MyReceiver;

import java.util.Calendar;

public class AlarmTriggerService extends IntentService {

    private static final String TAG = "AlarmTriggerService";
    PendingIntent pendingIntent;
    Preferences preferences;

    public AlarmTriggerService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        preferences = new Preferences(this);

        Intent myIntent = new Intent(this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent,0);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC,
                Calendar.getInstance().getTimeInMillis(),
                AlarmManager.INTERVAL_HOUR,
                pendingIntent);
    }
}
