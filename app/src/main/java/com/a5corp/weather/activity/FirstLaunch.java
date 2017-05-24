package com.a5corp.weather.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.a5corp.weather.R;
import com.a5corp.weather.fragment.FirstLaunchFragment;
import com.a5corp.weather.fragment.LoadingFragment;

public class FirstLaunch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, new FirstLaunchFragment())
                .commit();
        setSupportActionBar(toolbar);
    }

    public void execute() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, new LoadingFragment())
                .commit();
    }
}
