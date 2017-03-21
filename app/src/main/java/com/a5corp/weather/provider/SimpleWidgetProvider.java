package com.a5corp.weather.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.a5corp.weather.R;
import com.a5corp.weather.internet.FetchWeather;
import com.a5corp.weather.preferences.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class SimpleWidgetProvider extends AppWidgetProvider {

    FetchWeather wt;
    Context mContext;
    JSONObject json;
    RemoteViews remoteViews;
    Preferences preferences;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        mContext = context;
        for (int widgetId : appWidgetIds) {
            remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.simple_widget);
            getWeather();

            Intent intent = new Intent(context, SimpleWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_button_refresh, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    public void getWeather() {
        wt = new FetchWeather(mContext);
        preferences = new Preferences(mContext);
        new Thread() {
            public void run() {
                try {
                    json = wt.execute(preferences.getLastCity()).get()[0];
                } catch (InterruptedException iex) {
                    Log.e("InterruptedException", "iex");
                } catch (ExecutionException eex) {
                    Log.e("ExecutionException", "eex");
                }
                renderWeather(json);
                Log.i("Set" , "JSON");
            }
        }.start();
    }

    public void renderWeather(JSONObject json) {
        try {
            remoteViews.setTextViewText(R.id.widget_city, json.getString("name"));
            String ut;
            if (preferences.getUnits().equals("metric"))
                ut = "°C";
            else
                ut = "°F";
            remoteViews.setTextViewText(R.id.widget_temperature, ((int) json.getJSONObject("main").getDouble("temp")) + ut);
            String rs = json.getJSONArray("weather").getJSONObject(0).getString("description");
            String[] strArray = rs.split(" ");
            StringBuilder builder = new StringBuilder();
            for (String s : strArray) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap.concat(" "));
            }
            remoteViews.setTextViewText(R.id.widget_description, builder.toString());
            remoteViews.setTextViewText(R.id.widget_icon, setWeatherIcon(json.getJSONArray("weather").getJSONObject(0).getInt("id")));
            Log.i("Sets", "Widget");
        }
        catch (JSONException jex) {
            jex.printStackTrace();
        }
    }

    private String setWeatherIcon(int id) {
        String icon = "";
            if (checkDay())
                switch (id) {
                    case 501:
                        icon = mContext.getString(R.string.day_drizzle);
                        break;
                    case 500:
                        icon = mContext.getString(R.string.day_drizzle);
                        break;
                    case 502:
                        icon = mContext.getString(R.string.day_rainy);
                        break;
                    case 503:
                        icon = mContext.getString(R.string.day_rainy);
                        break;
                    case 504:
                        icon = mContext.getString(R.string.day_rainy);
                        break;
                    case 511:
                        icon = mContext.getString(R.string.day_rain_wind);
                        break;
                    case 520:
                        icon = mContext.getString(R.string.day_rain_drizzle);
                        break;
                    case 521:
                        icon = mContext.getString(R.string.day_drizzle);
                        break;
                    case 522:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 531:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 200:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 201:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 202:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 210:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 211:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 212:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 221:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 230:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 231:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 232:
                        icon = mContext.getString(R.string.day_thunder);
                        break;
                    case 300:
                        icon = mContext.getString(R.string.day_rain_drizzle);
                        break;
                    case 301:
                        icon = mContext.getString(R.string.day_rain_drizzle);
                        break;
                    case 302:
                        icon = mContext.getString(R.string.day_heavy_drizzle);
                        break;
                    case 310:
                        icon = mContext.getString(R.string.day_rain_drizzle);
                        break;
                    case 311:
                        icon = mContext.getString(R.string.day_rain_drizzle);
                        break;
                    case 312:
                        icon = mContext.getString(R.string.day_heavy_drizzle);
                        break;
                    case 313:
                        icon = mContext.getString(R.string.day_rain_drizzle);
                        break;
                    case 314:
                        icon = mContext.getString(R.string.day_heavy_drizzle);
                        break;
                    case 321:
                        icon = mContext.getString(R.string.day_heavy_drizzle);
                        break;
                    case 600:
                        icon = mContext.getString(R.string.day_snowy);
                        break;
                    case 601:
                        icon = mContext.getString(R.string.day_snowy);
                        break;
                    case 602:
                        icon = mContext.getString(R.string.snow);
                        break;
                    case 611:
                        icon = mContext.getString(R.string.sleet);
                        break;
                    case 612:
                        icon = mContext.getString(R.string.day_snowy);
                        break;
                    case 903:
                    case 615:
                        icon = mContext.getString(R.string.day_snowy);
                        break;
                    case 616:
                        icon = mContext.getString(R.string.day_snowy);
                        break;
                    case 620:
                        icon = mContext.getString(R.string.day_snowy);
                        break;
                    case 621:
                        icon = mContext.getString(R.string.day_snowy);
                        break;
                    case 622:
                        icon = mContext.getString(R.string.day_snowy);
                        break;
                    case 701:
                    case 702:
                    case 721:
                        icon = mContext.getString(R.string.smoke);
                        break;
                    case 751:
                    case 761:
                    case 731:
                        icon = mContext.getString(R.string.dust);
                        break;
                    case 741:
                        icon = mContext.getString(R.string.fog);
                        break;
                    case 762:
                        icon = mContext.getString(R.string.volcano);
                        break;
                    case 771:
                    case 900:
                    case 781:
                        icon = mContext.getString(R.string.tornado);
                        break;
                    case 904:
                        icon = mContext.getString(R.string.day_clear);
                        break;
                    case 800:
                        icon = mContext.getString(R.string.day_clear);
                        break;
                    case 801:
                        icon = mContext.getString(R.string.day_cloudy);
                        break;
                    case 802:
                        icon = mContext.getString(R.string.day_cloudy);
                        break;
                    case 803:
                        icon = mContext.getString(R.string.day_cloudy);
                        break;
                    case 804:
                        icon = mContext.getString(R.string.day_cloudy);
                        break;
                    case 901:
                        icon = mContext.getString(R.string.storm_showers);
                        break;
                    case 902:
                        icon = mContext.getString(R.string.hurricane);
                        break;
                }
            else
                switch (id) {
                    case 501:
                        icon = mContext.getString(R.string.night_drizzle);
                        break;
                    case 500:
                        icon = mContext.getString(R.string.night_drizzle);
                        break;
                    case 502:
                        icon = mContext.getString(R.string.night_rainy);
                        break;
                    case 503:
                        icon = mContext.getString(R.string.night_rainy);
                        break;
                    case 504:
                        icon = mContext.getString(R.string.night_rainy);
                        break;
                    case 511:
                        icon = mContext.getString(R.string.night_rain_wind);
                        break;
                    case 520:
                        icon = mContext.getString(R.string.night_rain_drizzle);
                        break;
                    case 521:
                        icon = mContext.getString(R.string.night_drizzle);
                        break;
                    case 522:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 531:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 200:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 201:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 202:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 210:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 211:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 212:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 221:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 230:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 231:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 232:
                        icon = mContext.getString(R.string.night_thunder);
                        break;
                    case 300:
                        icon = mContext.getString(R.string.night_rain_drizzle);
                        break;
                    case 301:
                        icon = mContext.getString(R.string.night_rain_drizzle);
                        break;
                    case 302:
                        icon = mContext.getString(R.string.night_heavy_drizzle);
                        break;
                    case 310:
                        icon = mContext.getString(R.string.night_rain_drizzle);
                        break;
                    case 311:
                        icon = mContext.getString(R.string.night_rain_drizzle);
                        break;
                    case 312:
                        icon = mContext.getString(R.string.night_heavy_drizzle);
                        break;
                    case 313:
                        icon = mContext.getString(R.string.night_rain_drizzle);
                        break;
                    case 314:
                        icon = mContext.getString(R.string.night_heavy_drizzle);
                        break;
                    case 321:
                        icon = mContext.getString(R.string.night_heavy_drizzle);
                        break;
                    case 600:
                        icon = mContext.getString(R.string.night_snowy);
                        break;
                    case 601:
                        icon = mContext.getString(R.string.night_snowy);
                        break;
                    case 602:
                        icon = mContext.getString(R.string.snow);
                        break;
                    case 611:
                        icon = mContext.getString(R.string.sleet);
                        break;
                    case 612:
                        icon = mContext.getString(R.string.night_snowy);
                        break;
                    case 903:
                    case 615:
                        icon = mContext.getString(R.string.night_snowy);
                        break;
                    case 616:
                        icon = mContext.getString(R.string.night_snowy);
                        break;
                    case 620:
                        icon = mContext.getString(R.string.night_snowy);
                        break;
                    case 621:
                        icon = mContext.getString(R.string.night_snowy);
                        break;
                    case 622:
                        icon = mContext.getString(R.string.night_snowy);
                        break;
                    case 701:
                    case 702:
                    case 721:
                        icon = mContext.getString(R.string.smoke);
                        break;
                    case 751:
                    case 761:
                    case 731:
                        icon = mContext.getString(R.string.dust);
                        break;
                    case 741:
                        icon = mContext.getString(R.string.fog);
                        break;
                    case 762:
                        icon = mContext.getString(R.string.volcano);
                        break;
                    case 771:
                    case 900:
                    case 781:
                        icon = mContext.getString(R.string.tornado);
                        break;
                    case 904:
                        icon = mContext.getString(R.string.night_clear);
                        break;
                    case 800:
                        icon = mContext.getString(R.string.night_clear);
                        break;
                    case 801:
                        icon = mContext.getString(R.string.night_cloudy);
                        break;
                    case 802:
                        icon = mContext.getString(R.string.night_cloudy);
                        break;
                    case 803:
                        icon = mContext.getString(R.string.night_cloudy);
                        break;
                    case 804:
                        icon = mContext.getString(R.string.night_cloudy);
                        break;
                    case 901:
                        icon = mContext.getString(R.string.storm_showers);
                        break;
                    case 902:
                        icon = mContext.getString(R.string.hurricane);
                        break;
                }
        Log.i("Returning" , "Icon");
        return icon;
    }

    private boolean checkDay() {
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);

        return !(hours >= 18 || hours <= 6);
    }
}