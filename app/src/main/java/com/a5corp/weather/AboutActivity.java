package com.a5corp.weather;

import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView madeBy = (TextView) findViewById(R.id.made_by);
        TextView sourceAt = (TextView) findViewById(R.id.source_at);
        TextView icons = (TextView) findViewById(R.id.icons);
        TextView owmField = (TextView) findViewById(R.id.owm_field);
        TextView appIcon = (TextView) findViewById(R.id.app_icon);
        TextView verText = (TextView) findViewById(R.id.ver_text);

        sourceAt.setMovementMethod(LinkMovementMethod.getInstance());           //To make the link clickable
        madeBy.setMovementMethod(LinkMovementMethod.getInstance());
        icons.setMovementMethod(LinkMovementMethod.getInstance());
        owmField.setMovementMethod(LinkMovementMethod.getInstance());

        Typeface weatherFont;
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weather.ttf");
        appIcon.setTypeface(weatherFont);
        appIcon.setText(getString(R.string.weather_sunny_cloudy));
        String verId = "Version " + BuildConfig.VERSION_NAME;
        verText.setText(verId);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
