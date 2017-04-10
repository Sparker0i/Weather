package com.a5corp.weather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.a5corp.weather.R;
import com.a5corp.weather.internet.CheckConnection;
import com.a5corp.weather.internet.FetchWeatherOther;
import com.a5corp.weather.model.WeatherInfo;
import com.a5corp.weather.preferences.Preferences;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class LargeWidgetProvider extends AppWidgetProvider {
    WeatherInfo json;
    Context context;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        int[] allids = AppWidgetManager
                .getInstance(context)
                .getAppWidgetIds(new ComponentName(context, LargeWidgetProvider.class));
        Intent intent = new Intent(context , LargeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allids);
        context.sendBroadcast(intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.context = context;
        try {
            Preferences preferences = new Preferences(context);
            CheckConnection connection = new CheckConnection(context);
            for (int widgetId : appWidgetIds) {
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.widget_large);
                loadFromPreference(preferences , remoteViews , appWidgetManager , appWidgetIds , widgetId);
                FetchWeatherOther wt = new FetchWeatherOther(context);
                if (!connection.isNetworkAvailable())
                    return;
                json = wt.execute(new Preferences(context).getCity()).get();
                preferences.storeLargeWidget(new Gson().toJson(json));
                double temp = json.getMain().getTemp();
                /*
                    PROTECTED : DO NOT TOUCH THE SECTION BELOW
                 */
                Intent intent = new Intent(context, LargeWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.widget_button_refresh, pendingIntent);

                remoteViews.setTextViewText(R.id.widget_city, json.getName() +
                        ", " +
                        json.getSys().getCountry());
                String ut = new Preferences(context).getUnits().equals("metric") ? "C" : "F";
                remoteViews.setTextViewText(R.id.widget_temperature, Integer.toString((int) temp) + "°" + ut);
                setWeatherIcon(json.getWeather().get(0).getId() , context , remoteViews);
                String rs = json.getWeather().get(0).getDescription();
                String[] strArray = rs.split(" ");
                StringBuilder builder = new StringBuilder();
                for (String s : strArray) {
                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                    builder.append(cap.concat(" "));
                }

                remoteViews.setTextViewText(R.id.widget_description , builder.toString());
                remoteViews.setTextViewText(R.id.widget_wind , "Wind : " + json.getWind().getSpeed() + " m/" + (preferences.getUnits().equals("metric") ? "s" : "h"));
                remoteViews.setTextViewText(R.id.widget_humidity , "Humidity : " + json.getMain().getHumidity() + " %");
                remoteViews.setTextViewText(R.id.widget_pressure , "Pressure : " + json.getMain().getPressure() + " hPa");

                appWidgetManager.updateAppWidget(widgetId, remoteViews);

                Log.i("In" , "Large Widget");
            }
        }
        catch (JSONException | InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    private boolean checkDay() {
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);

        return !(hours >= 18 || hours <= 6);
    }

    private void setWeatherIcon(int id , Context mContext , RemoteViews remoteViews) {
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
        remoteViews.setImageViewBitmap(R.id.widget_icon , createWeatherIcon(mContext , icon));
    }

    private void loadFromPreference(Preferences preferences , RemoteViews remoteViews , AppWidgetManager appWidgetManager , int[] appWidgetIds , int widgetId) throws JSONException{
        WeatherInfo json;
        if (preferences.getLargeWidget() != null)
            json = new Gson().fromJson(preferences.getLargeWidget() , WeatherInfo.class);
        else
            return;
        double temp = json.getMain().getTemp();

        Intent intent = new Intent(context, LargeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_button_refresh, pendingIntent);

        remoteViews.setTextViewText(R.id.widget_city, json.getName() +
                ", " +
                json.getSys().getCountry());
        String ut = new Preferences(context).getUnits().equals("metric") ? "C" : "F";
        remoteViews.setTextViewText(R.id.widget_temperature, Integer.toString((int) temp) + "°" + ut);
        setWeatherIcon(json.getWeather().get(0).getId() , context , remoteViews);
        String rs = json.getWeather().get(0).getDescription();
        String[] strArray = rs.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap.concat(" "));
        }

        remoteViews.setTextViewText(R.id.widget_description , builder.toString());
        remoteViews.setTextViewText(R.id.widget_wind , "Wind : " + json.getWind().getSpeed() + " m/" + (preferences.getUnits().equals("metric") ? "s" : "h"));
        remoteViews.setTextViewText(R.id.widget_humidity , "Humidity : " + json.getMain().getHumidity() + " %");
        remoteViews.setTextViewText(R.id.widget_pressure , "Pressure : " + json.getMain().getPressure() + " hPa");

        appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }

    public static Bitmap createWeatherIcon(Context context, String text) {
        Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Typeface weatherFont = Typeface.createFromAsset(context.getAssets(),
                "fonts/weather.ttf");
        int textColor = ContextCompat.getColor(context, R.color.textColor);

        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(weatherFont);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(textColor);
        paint.setTextSize(180);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, 128, 200, paint);
        return bitmap;
    }
}