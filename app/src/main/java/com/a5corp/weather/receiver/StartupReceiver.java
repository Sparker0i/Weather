package com.a5corp.weather.receiver;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.a5corp.weather.model.Log;

import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.service.NotificationService;
import com.a5corp.weather.widget.LargeWidgetProvider;
import com.a5corp.weather.widget.SmallWidgetProvider;

public class StartupReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent intent2 = new Intent(context, LargeWidgetProvider.class);
            intent2.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, LargeWidgetProvider.class));
            intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            context.sendBroadcast(intent2);

            intent2 = new Intent(context, LargeWidgetProvider.class);
            intent2.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, SmallWidgetProvider.class));
            intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            context.sendBroadcast(intent2);

            NotificationService.enqueueWork(context, intent);
        }
        else
            Log.i("No" , "Boot");
    }
}

