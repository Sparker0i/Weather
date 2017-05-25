package com.a5corp.weather.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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
                .add(R.id.fragment_first , new FirstLaunchFragment())
                .commit();
        setSupportActionBar(toolbar);
    }

    public void execute() {
        Log.i("In" , "Execute " + FirstLaunch.class.getSimpleName());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_first , new LoadingFragment())
                .commit();
    }
}
