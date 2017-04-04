package com.a5corp.weather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.a5corp.weather.service.NotificationBuilderService;

public class MyReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent service1 = new Intent(context, NotificationBuilderService.class);
        context.startService(service1);
    }
}
