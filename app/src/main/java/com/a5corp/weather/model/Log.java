package com.a5corp.weather.model;

import android.os.Build;

import com.a5corp.weather.BuildConfig;

public class Log {
    public static void i(String tag , String message) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(tag , message);
    }

    public static void i(String tag , String message , Throwable r) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(tag, message, r);
    }

    public static void d(String tag , String message) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(tag , message);
    }

    public static void d(String tag , String message , Throwable r) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(tag, message, r);
    }

    public static void e(String tag , String message) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(tag , message);
    }

    public static void e(String tag , String message , Throwable r) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(tag, message, r);
    }
}
