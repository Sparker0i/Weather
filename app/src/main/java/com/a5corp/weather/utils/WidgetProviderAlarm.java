package com.a5corp.weather.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class WidgetProviderAlarm {

    private static final String TAG = "AppWidgetProviderAlarm";

    private Context mContext;
    private Class<?> mCls;

    public WidgetProviderAlarm(Context context, Class<?> cls) {
        this.mContext = context;
        this.mCls = cls;
    }

    public void setAlarm() {
        String updatePeriodStr = "60";
        long updatePeriodMills = AlarmManager.INTERVAL_HOUR;
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + updatePeriodMills,
                updatePeriodMills,
                getPendingIntent(mCls));
    }

    public void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(mCls));
        getPendingIntent(mCls).cancel();
    }

    private PendingIntent getPendingIntent(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        return PendingIntent.getBroadcast(mContext,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public boolean isAlarmOff() {
        Intent intent = new Intent(mContext, mCls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
                0,
                intent,
                PendingIntent.FLAG_NO_CREATE);
        return pendingIntent == null;
    }
}
