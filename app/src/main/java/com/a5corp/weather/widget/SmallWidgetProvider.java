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
import com.a5corp.weather.preferences.SWPrefs;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Utils;
import com.a5corp.weather.utils.WidgetProviderAlarm;

import java.util.Locale;

public class SmallWidgetProvider extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        WidgetProviderAlarm appWidgetProviderAlarm =
                new WidgetProviderAlarm(context, SmallWidgetProvider.class);
        appWidgetProviderAlarm.setAlarm();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int... appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i("Trigger2" , "Small Widget");
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_small);

            preLoadWeather(context , remoteViews);
            Intent intent = new Intent(context, SmallWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_button_refresh, pendingIntent);

            Intent intentStartActivity = new Intent(context, WeatherActivity.class);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intentStartActivity, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_root, pendingIntent2);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
        context.startService(new Intent(context, SmallWidgetService.class));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        WidgetProviderAlarm appWidgetProviderAlarm =
                new WidgetProviderAlarm(context, SmallWidgetProvider.class);
        appWidgetProviderAlarm.cancelAlarm();
    }

    private void preLoadWeather(Context context, RemoteViews remoteViews) {
        SWPrefs SWPrefs = new SWPrefs(context);
        Prefs prefs = new Prefs(context);
        String temperatureScale = prefs.getUnits().equals("metric") ? context.getString(R.string.c) : context.getString(R.string.f);

        String temperature = String.format(Locale.getDefault(), "%.0f", SWPrefs.getTemperature());
        int iconId = SWPrefs.getIcon();
        String weatherIcon = Utils.getStrIcon(iconId , context);

        remoteViews.setTextViewText(R.id.widget_city, SWPrefs.getCity() + ", " + SWPrefs.getCountry());
        remoteViews.setTextViewText(R.id.widget_temperature, temperature + temperatureScale);
        remoteViews.setImageViewBitmap(R.id.widget_icon,
                Utils.createWeatherIcon(context, weatherIcon));
    }
}