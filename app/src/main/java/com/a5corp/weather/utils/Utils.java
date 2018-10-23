package com.a5corp.weather.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import androidx.core.content.ContextCompat;

import com.a5corp.weather.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class Utils {

    public static String setWeatherIcon(Context context, int id) {
        id /= 100;
        String icon = "";
        if (id * 100 == 800) {
            int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            if (hourOfDay >= 7 && hourOfDay < 20) {
                icon = context.getString(R.string.weather_sunny);
            }
            else {
                icon = context.getString(R.string.weather_clear_night);
            }
        }
        else {
            switch (id) {
                case 2:
                    icon = context.getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = context.getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = context.getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = context.getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = context.getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = context.getString(R.string.weather_rainy);
                    break;
            }
        }
        return icon;
    }
    
    public static URL getWeatherForecastUrl(String endpoint, String city, String units) throws
            MalformedURLException {
        String builtDay = Uri.parse(endpoint).buildUpon()
                .appendQueryParameter(Constants.QUERY_PARAM , city)
                .appendQueryParameter(Constants.FORMAT_PARAM , Constants.FORMAT_VALUE)
                .appendQueryParameter(Constants.UNITS_PARAM , units)
                .appendQueryParameter(Constants.DAYS_PARAM , Integer.toString(10))
                .build()
                .toString();
        return new URL(builtDay);
    }

    public static Bitmap createWeatherIcon(Context context, String text) {
        Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Typeface weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weather-icons-v2.0.10.ttf");
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(weatherFont);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(context , R.color.textColor));
        paint.setTextSize(180);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, 128, 200, paint);
        return bitmap;
    }
}
