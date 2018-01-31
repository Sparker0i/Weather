package com.a5corp.weather.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.preference.PreferenceManager;
import com.a5corp.weather.model.Log;

import com.a5corp.weather.R;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.LanguageUtil;

import java.util.Locale;

public class MyApplication extends Application {
    private Locale locale = null;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();

        String lang = preferences.getString(getString(R.string.pref_language) , "en");
        locale = new Locale(lang);
        config.setLocale(locale);
        Log.i("Locale" , lang);
        Locale.setDefault(locale);
        updateConfiguration(config);
        setSystemLocale(config , locale);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            LanguageUtil.setLanguage(this, new Prefs(this).getLanguage());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            setSystemLocale(newConfig, locale);
            Locale.setDefault(locale);
            updateConfiguration(newConfig);
        }
    }

    @SuppressWarnings("deprecation")
    private static void setSystemLocale(Configuration config, Locale locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
    }

    @SuppressWarnings("deprecation")
    private void updateConfiguration(Configuration config) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getBaseContext().createConfigurationContext(config);
        } else {
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
    }
}