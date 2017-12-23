package com.a5corp.weather.widget;

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

public class SmallWidgetService extends JobIntentService{

    private static final String TAG = "smallWidgetService";

    public SmallWidgetService() {
        super();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        CheckConnection cc = new CheckConnection(this);
        if (!cc.isNetworkAvailable())
            return;

        Prefs SWPrefs = new Prefs(this);
        String city = SWPrefs.getCity();
        String units = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_TEMPERATURE_UNITS , Constants.METRIC);

        try {
            WeatherInfo weatherRaw = new Request(this).getItems(city, units);
            SWPrefs.saveWeather(weatherRaw);
            updateWidget(weatherRaw);
        } catch (IOException e) {
            Log.e(TAG, "Could not retrieve Weather", e);
            stopSelf();
        }
    }

    public static void enqueueWork(Context context , Intent intent) {
        enqueueWork(context , SmallWidgetService.class , 0x01 , intent);
    }

    private void updateWidget(WeatherInfo weather) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        ComponentName widgetComponent = new ComponentName(this, SmallWidgetProvider.class);

        Prefs prefs = new Prefs(this);

        int[] widgetIds = widgetManager.getAppWidgetIds(widgetComponent);
        for (int appWidgetId : widgetIds) {
            String temperatureScale = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_TEMPERATURE_UNITS , Constants.METRIC).equals(Constants.METRIC) ? getString(R.string.c) : getString(R.string.f);

            String temperature = String.format(Locale.getDefault(), "%.0f", weather.getMain().getTemp());
            int iconId = weather.getWeather().get(0).getId();
            String weatherIcon = Utils.setWeatherIcon( this, iconId, 0);

            RemoteViews remoteViews = new RemoteViews(this.getPackageName(),
                    R.layout.widget_small);
            remoteViews.setTextViewText(R.id.widget_city, weather.getName() + "," + weather.getSys().getCountry());
            remoteViews.setTextViewText(R.id.widget_temperature, temperature + temperatureScale);

            remoteViews.setImageViewBitmap(R.id.widget_icon,
                    Utils.createWeatherIcon(this, weatherIcon));

            widgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }
}
