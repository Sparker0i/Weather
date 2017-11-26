package com.a5corp.weather.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import com.a5corp.weather.model.Log;
import android.widget.RemoteViews;

import com.a5corp.weather.R;
import com.a5corp.weather.internet.CheckConnection;
import com.a5corp.weather.internet.Request;
import com.a5corp.weather.model.WeatherInfo;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Constants;
import com.a5corp.weather.utils.Utils;

import java.io.IOException;
import java.util.Locale;

public class LargeWidgetService extends JobIntentService {

    private static final String TAG = "LargeWidgetService";

    public static void enqueueWork(Context context , Intent intent) {
        enqueueWork(context , LargeWidgetService.class, 0x01 , intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        CheckConnection cc = new CheckConnection(this);
        if (!cc.isNetworkAvailable())
            return;

        Prefs lwPrefs = new Prefs(this);
        String city = lwPrefs.getCity();
        String units = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_TEMPERATURE_UNITS , Constants.METRIC);

        try {
            WeatherInfo weatherRaw = new Request(this).getItems(city, units);
            lwPrefs.saveWeather(weatherRaw);
            updateWidget(weatherRaw);
        } catch (IOException e) {
            Log.e(TAG, "Could not retrieve Weather", e);
            stopSelf();
        }
    }

    private void updateWidget(WeatherInfo weather) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        ComponentName widgetComponent = new ComponentName(this, LargeWidgetProvider.class);

        Prefs prefs = new Prefs(this);

        int[] widgetIds = widgetManager.getAppWidgetIds(widgetComponent);
        for (int appWidgetId : widgetIds) {
            String temperatureScale = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_TEMPERATURE_UNITS , Constants.METRIC).equals(Constants.METRIC) ? getString(R.string.c) : getString(R.string.f);
            String speedScale = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_TEMPERATURE_UNITS , Constants.METRIC).equals(Constants.METRIC) ? getString(R.string.mps) : getString(R.string.mph);

            String temperature = String.format(Locale.getDefault(), "%.0f", weather.getMain().getTemp());
            String wind = getString(R.string.wind_, weather.getWind().getSpeed(), speedScale);
            String humidity = getString(R.string.humidity, weather.getMain().getHumidity());
            String pressure = getString(R.string.pressure, weather.getMain().getPressure());
            int iconId = weather.getWeather().get(0).getId();
            String weatherIcon = Utils.setWeatherIcon( this, iconId, 0);

            RemoteViews remoteViews = new RemoteViews(this.getPackageName(),
                    R.layout.widget_large);
            remoteViews.setTextViewText(R.id.widget_city, weather.getName() + ", " + weather.getSys().getCountry());
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
                    Utils.createWeatherIcon(this, weatherIcon));

            widgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }
}
