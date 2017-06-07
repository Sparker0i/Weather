package com.a5corp.weather.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;

import com.a5corp.weather.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class Utils {
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

    private static boolean checkDay() {
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);

        return !(hours >= 18 || hours <= 6);
    }

    public static String getStrIcon(int id , Context mContext) {
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
        return icon;
    }

    public static Bitmap createWeatherIcon(Context context, String text) {
        Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Typeface weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weather.ttf");
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
