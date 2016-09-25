package com.a5corp.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class GlobalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
        Log.i("Location" , "Global");
        Intent intent = new Intent(GlobalActivity.this, WeatherActivity.class);
        GlobalActivity.this.startActivity(intent);
    }
}
