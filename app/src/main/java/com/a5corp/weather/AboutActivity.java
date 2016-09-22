package com.a5corp.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView madeBy = (TextView) findViewById(R.id.madeBy);
        madeBy.setText("Made By Aaditya Menon");
    }
}
