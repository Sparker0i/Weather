package com.a5corp.weather.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import com.a5corp.weather.model.Log;
import android.widget.RemoteViews;

import com.a5corp.weather.BuildConfig;
import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.model.WeatherInfo;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Constants;
import com.a5corp.weather.utils.Utils;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LargeWidgetProvider extends AbstractWidgetProvider {

    private static final String TAG = "LargeWidgetProvider";

    private static final String ACTION_UPDATE_TIME = "com.a5corp.weather.UPDATE_TIME";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            com.a5corp.weather.model.Log.i("In" , "New Widget Provider");
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_large);

            Intent intent = new Intent(context, WeatherActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_root, pendingIntent);

            intent = new Intent(context, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_button_refresh, pendingIntent);

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            WeatherInfo weather;
            if(!sp.getString("lastToday", "").equals("")) {
                weather = parseWidgetJson(sp.getString("lastToday", ""));
            }
            else {
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
                return;
            }
            String temperatureScale = PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.PREF_TEMPERATURE_UNITS , Constants.METRIC).equals(Constants.METRIC) ? context.getString(R.string.c) : context.getString(R.string.f);
            String speedScale = PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.PREF_TEMPERATURE_UNITS , Constants.METRIC).equals(Constants.METRIC) ? context.getString(R.string.mps) : context.getString(R.string.mph);
            String temperature = String.format(Locale.getDefault(), "%.0f", weather.getMain().getTemp());
            String wind = context.getString(R.string.wind_, weather.getWind().getSpeed(), speedScale);
            String humidity = context.getString(R.string.humidity, weather.getMain().getHumidity());
            String pressure = context.getString(R.string.pressure, weather.getMain().getPressure());
            int iconId = weather.getWeather().get(0).getId();
            String weatherIcon = Utils.setWeatherIcon( context, iconId);

            remoteViews.setTextViewText(R.id.widget_city, weather.getName() + "," + weather.getSys().getCountry());
            remoteViews.setTextViewText(R.id.widget_temperature, temperature + temperatureScale);
            String rs = weather.getWeather().get(0).getDescription();
            String[] strArray = rs.split(" ");
            StringBuilder builder = new StringBuilder();
            for (String s : strArray) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap.concat(" "));
            }
            remoteViews.setTextViewText(R.id.widget_description,
                    builder.toString());
            remoteViews.setTextViewText(R.id.widget_wind, wind);
            remoteViews.setTextViewText(R.id.widget_humidity, humidity);
            remoteViews.setTextViewText(R.id.widget_pressure, pressure);
            remoteViews.setImageViewBitmap(R.id.widget_icon,
                    Utils.createWeatherIcon(context, weatherIcon));

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        scheduleNextUpdate(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName provider = new ComponentName(context.getPackageName(), getClass().getName());
        int ids[] = appWidgetManager.getAppWidgetIds(provider);
        onUpdate(context, appWidgetManager, ids);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);

        Log.d(TAG, "Disable simple widget updates");
        cancelUpdate(context);
    }

    private static void scheduleNextUpdate(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long now = new Date().getTime();
        long nextUpdate = now + Long.parseLong(new Prefs(context).getTime());
        Log.i(TAG, "Next widget update: " +
                android.text.format.DateFormat.getTimeFormat(context).format(new Date(nextUpdate)));
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC, nextUpdate, getTimeIntent(context));
        } else {
            alarmManager.set(AlarmManager.RTC, nextUpdate, getTimeIntent(context));
        }
    }

    private static void cancelUpdate(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getTimeIntent(context));
    }

    private static PendingIntent getTimeIntent(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(ACTION_UPDATE_TIME);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
