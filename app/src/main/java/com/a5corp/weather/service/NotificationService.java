package com.a5corp.weather.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.internet.CheckConnection;
import com.a5corp.weather.internet.Request;
import com.a5corp.weather.model.WeatherInfo;
import com.a5corp.weather.preferences.Prefs;

import java.io.IOException;

public class NotificationService extends IntentService {

    private static final String TAG = "NotificationsService";
    Prefs prefs;

    public NotificationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        CheckConnection checkNetwork = new CheckConnection(this);
        if (!checkNetwork.isNetworkAvailable()) {
            return;
        }

        prefs = new Prefs(this);
        String city = prefs.getCity();
        String units = prefs.getUnits();

        try {
            WeatherInfo weather;
            weather = new Request(this).getItems(city, units);
            weatherNotification(weather);
        } catch (IOException e) {
            Log.e(TAG, "Error get weather", e);
        }
    }

    public static Intent newIntent(Context context) {
        Log.i("Trigger" , "newIntent");
        return new Intent(context, NotificationService.class);
    }

    public static void setNotificationServiceAlarm(Context context,
                                                   boolean isNotificationEnable) {
        Intent intent = NotificationService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long intervalMillis = AlarmManager.INTERVAL_HOUR;
        if (isNotificationEnable) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(),
                    intervalMillis,
                    pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    private void weatherNotification(WeatherInfo weather) {
        Intent intent = new Intent(this, WeatherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String temperatureScale = prefs.getUnits().equals("metric") ? getString(R.string.c) : getString(R.string.f);
        String speedScale = prefs.getUnits().equals("metric") ? getString(R.string.mps) : getString(R.string.mph);

        String temperature = getString(R.string.temperature , weather.getMain().getTemp() , temperatureScale);
        String city = getString(R.string.city , weather.getName() + ", " + weather.getSys().getCountry());
        String wind = getString(R.string.wind_ , weather.getWind().getSpeed(), speedScale);
        String humidity = getString(R.string.humidity , weather.getMain().getHumidity());
        String pressure = getString(R.string.pressure, weather.getMain().getPressure());

        String data = city + "\n" + temperature + "\n" + wind + "\n" + humidity + "\n" + pressure;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setAutoCancel(false);
        builder.setContentTitle("Weather Notification");
        builder.setContentText(Math.round(weather.getMain().getTemp()) + temperatureScale + " at " + weather.getName());
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(data));
        builder.setSmallIcon(R.drawable.ic_notification_icon);
        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= 24)
            builder.setColor(Color.parseColor("#ff0000"));
        Notification notification = builder.build();
        notificationManager.notify(0 , notification);
    }
}