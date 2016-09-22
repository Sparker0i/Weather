package com.a5corp.weather;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView madeBy = (TextView) findViewById(R.id.madeBy);
        madeBy.setText("Made By Aaditya Menon");
        TextView sourceAt = (TextView) findViewById(R.id.sourceAt);
        sourceAt.setMovementMethod(LinkMovementMethod.getInstance());
        Typeface weatherFont;
        TextView appIcon = (TextView) findViewById(R.id.appIcon);
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weather.ttf");
        appIcon.setTypeface(weatherFont);
        appIcon.setText(getString(R.string.weather_sunny_cloudy));
    }
}
