package com.a5corp.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class GlobalActivity extends Activity {

    static SharedPreferences prefs = null;
    static String firstRun = "first_run";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
        prefs = getSharedPreferences("com.a5corp.weather.GlobalActivity",MODE_PRIVATE);
        Log.i("Loaded" , "Global");
        /*userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.i("Loaded" , "Global");
        boolean loggedIn = userInfo.getBoolean("loggedIn" , false);
        Log.i("loggedIn" , (loggedIn == true) ? "True" : "False");
        if (loggedIn) {
            userInfo.edit().putBoolean("loggedIn" , true);
            Intent intent = new Intent(GlobalActivity.this, WeatherActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Log.i("Loaded" , "Weather");
            startActivity(intent);
        } else {
            Intent intent = new Intent(GlobalActivity.this, FirstLaunch.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Log.i("Loaded" , "First");
            startActivity(intent);
        }*/
    }

    public boolean firstTime(){
        return prefs.getBoolean(firstRun,false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (firstTime()) {
            Intent intent = new Intent(GlobalActivity.this, WeatherActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Log.i("Loaded" , "Weather");
            startActivity(intent);
        }
        else {
            prefs.edit().putBoolean(firstRun , false);
            Intent intent = new Intent(GlobalActivity.this, FirstLaunch.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Log.i("Loaded" , "First");
            startActivity(intent);
        }
    }
}
