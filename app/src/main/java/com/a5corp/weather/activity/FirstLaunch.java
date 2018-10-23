package com.a5corp.weather.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.a5corp.weather.R;
import com.a5corp.weather.app.MyContextWrapper;
import com.a5corp.weather.fragment.FirstLaunchFragment;
import com.a5corp.weather.preferences.Prefs;

public class FirstLaunch extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = MyContextWrapper.wrap(newBase, new Prefs(newBase).getLanguage());
        super.attachBaseContext(context);
    }

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
