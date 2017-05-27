package com.a5corp.weather.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.a5corp.weather.R;
import com.a5corp.weather.fragment.FirstLaunchFragment;

public class FirstLaunch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment , new FirstLaunchFragment())
                .commit();
        setSupportActionBar(toolbar);
    }
}
