package com.a5corp.weather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
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
import com.a5corp.weather.internet.FetchWeather;
import com.a5corp.weather.preferences.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class LargeWidgetProvider extends AppWidgetProvider {
    JSONObject json;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        try {
            Preferences preferences = new Preferences(context);
            for (int widgetId : appWidgetIds) {
                FetchWeather wt = new FetchWeather(context);
                json = wt.execute(new Preferences(context).getCity()).get()[0];
                String number = json.getString("name");
                double temp = json.getJSONObject("main").getDouble("temp");
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.widget_small);
                remoteViews.setTextViewText(R.id.widget_city, number);
                String ut = new Preferences(context).getUnits().equals("metric") ? "C" : "F";
                remoteViews.setTextViewText(R.id.widget_temperature, Integer.toString((int) temp) + "°" + ut);
                setWeatherIcon(json.getJSONArray("weather").getJSONObject(0).getInt("id") , context , remoteViews);
                String rs = json.getJSONArray("weather").getJSONObject(0).getString("description");
                String[] strArray = rs.split(" ");
                StringBuilder builder = new StringBuilder();
                for (String s : strArray) {
                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                    builder.append(cap.concat(" "));
                }

                remoteViews.setTextViewText(R.id.widget_description , builder.toString());
                remoteViews.setTextViewText(R.id.widget_wind , "Wind : " + json.getJSONObject("wind").getLong("speed") + " m/" + (preferences.getUnits().equals("metric") ? "s" : "h"));
                remoteViews.setTextViewText(R.id.widget_humidity , "Humidity : " + json.getJSONObject("main").getLong("humidity") + " %");
                remoteViews.setTextViewText(R.id.widget_pressure , "Pressure : " + json.getJSONObject("main").getLong("pressure") + " hPa");

                Intent intent = new Intent(context, SmallWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.widget_button_refresh, pendingIntent);
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
