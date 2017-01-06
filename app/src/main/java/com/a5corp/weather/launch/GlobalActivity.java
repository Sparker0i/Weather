package com.a5corp.weather.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.a5corp.weather.R;
import com.a5corp.weather.WeatherActivity;
import com.a5corp.weather.preferences.Preferences;

public class GlobalActivity extends AppCompatActivity {

    public static Preferences cp;
    public static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
        Log.i("Loaded" , "Global");
    }

    @Override
    protected void onResume() {
        cp = new Preferences(this);
        super.onResume();

        if (!cp.getPrefs().getBoolean("first" , true)) {
            Intent intent = new Intent(GlobalActivity.this, WeatherActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Log.i("Loaded" , "Weather");
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(GlobalActivity.this, FirstLaunch.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Log.i("Loaded" , "First");
            startActivity(intent);
        }
    }
}
