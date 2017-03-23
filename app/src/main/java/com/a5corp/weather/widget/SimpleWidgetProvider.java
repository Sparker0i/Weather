package com.a5corp.weather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.a5corp.weather.R;
import com.a5corp.weather.internet.FetchWeather;
import com.a5corp.weather.preferences.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SimpleWidgetProvider extends AppWidgetProvider {

    JSONObject json;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        try {
            for (int widgetId : appWidgetIds) {
                FetchWeather wt = new FetchWeather(context);
                json = wt.execute(new Preferences(context).getCity()).get()[0];
                String number = json.getString("name");
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.simple_widget);
                remoteViews.setTextViewText(R.id.textView, number);

                Intent intent = new Intent(context, SimpleWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }
        catch (JSONException | InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }
}