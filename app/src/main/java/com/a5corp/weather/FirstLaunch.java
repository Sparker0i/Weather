package com.a5corp.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstLaunch extends AppCompatActivity {

    WeatherFragment wf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
        final TextView cityInput = (TextView) findViewById(R.id.city_input);
        Button goButton = (Button) findViewById(R.id.go_button);
        goButton.setText("GO");
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pf = CityPreference.getPrefs();
                pf.edit().putString("city" , cityInput.getText().toString()).apply();
                CityPreference.prefs = pf;
                Log.i("Changed" , "City");
            }
        });
    }
}