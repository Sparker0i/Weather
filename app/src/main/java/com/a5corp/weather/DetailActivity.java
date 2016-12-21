package com.a5corp.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);
        DetailFragment hello = new DetailFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_detail, hello, "HELLO").commit();
    }
}
