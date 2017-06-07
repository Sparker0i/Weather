package com.a5corp.weather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.preferences.LWPrefs;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Utils;
import com.a5corp.weather.utils.WidgetProviderAlarm;

import java.util.Locale;

public class LargeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        WidgetProviderAlarm appWidgetProviderAlarm =
                new WidgetProviderAlarm(context, LargeWidgetProvider.class);
        appWidgetProviderAlarm.setAlarm();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Trigger" , "Large Widget");
        context.startService(new Intent(context , LargeWidgetService.class));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i("Trigger2" , "Large Widget");
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_large);

            preLoadWeather(context , remoteViews);
            Intent intent = new Intent(context, LargeWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[appWidgetId]);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_button_refresh, pendingIntent);

            Intent intentStartActivity = new Intent(context, WeatherActivity.class);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0,
                    intentStartActivity, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_root, pendingIntent2);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
        context.startService(new Intent(context, LargeWidgetService.class));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        WidgetProviderAlarm appWidgetProviderAlarm =
                new WidgetProviderAlarm(context, LargeWidgetProvider.class);
        appWidgetProviderAlarm.cancelAlarm();
    }

    private void preLoadWeather(Context context, RemoteViews remoteViews) {
        LWPrefs lwPrefs = new LWPrefs(context);
        Prefs prefs = new Prefs(context);
        String temperatureScale = prefs.getUnits().equals("metric") ? context.getString(R.string.c) : context.getString(R.string.f);
        String speedScale = prefs.getUnits().equals("metric") ? context.getString(R.string.mps) : context.getString(R.string.mph);

        String temperature = String.format(Locale.getDefault(), "%.0f", lwPrefs.getTemperature());
        String description = lwPrefs.getDescription();
        String wind = context.getString(R.string.wind_speed, lwPrefs.getSpeed(), speedScale);
        String humidity = context.getString(R.string.humidity, lwPrefs.getHumidity());
        String pressure = context.getString(R.string.pressure, lwPrefs.getPressure());
        int iconId = lwPrefs.getIcon();
        String weatherIcon = Utils.getStrIcon(iconId , context);

        remoteViews.setTextViewText(R.id.widget_city, lwPrefs.getCity());
        remoteViews.setTextViewText(R.id.widget_temperature, temperature + temperatureScale);
        remoteViews.setTextViewText(R.id.widget_description, description);
        remoteViews.setTextViewText(R.id.widget_wind, wind);
        remoteViews.setTextViewText(R.id.widget_humidity, humidity);
        remoteViews.setTextViewText(R.id.widget_pressure, pressure);
        remoteViews.setImageViewBitmap(R.id.widget_icon,
                Utils.createWeatherIcon(context, weatherIcon));
    }
}