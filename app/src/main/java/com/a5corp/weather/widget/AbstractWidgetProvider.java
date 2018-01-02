package com.a5corp.weather.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.a5corp.weather.model.WeatherInfo;
import com.google.gson.Gson;

public abstract class AbstractWidgetProvider extends AppWidgetProvider {

    protected WeatherInfo parseWidgetJson(String result) {
        return new Gson().fromJson(result, WeatherInfo.class);
    }

    public static void updateWidgets(Context context) {
        updateWidgets(context, LargeWidgetProvider.class);
        updateWidgets(context, SmallWidgetProvider.class);
    }

    private static void updateWidgets(Context context, Class widgetClass) {
        Intent intent = new Intent(context.getApplicationContext(), widgetClass)
                .setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context.getApplicationContext())
                .getAppWidgetIds(new ComponentName(context.getApplicationContext(), widgetClass));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.getApplicationContext().sendBroadcast(intent);
    }
}