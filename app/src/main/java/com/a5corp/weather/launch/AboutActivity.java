package com.a5corp.weather.launch;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.a5corp.weather.BuildConfig;
import com.a5corp.weather.R;

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
        TextView matDialog = (TextView) findViewById(R.id.material_dialogs);

        sourceAt.setMovementMethod(LinkMovementMethod.getInstance());           //To make the link clickable
        madeBy.setMovementMethod(LinkMovementMethod.getInstance());
        icons.setMovementMethod(LinkMovementMethod.getInstance());
        owmField.setMovementMethod(LinkMovementMethod.getInstance());
        matDialog.setMovementMethod(LinkMovementMethod.getInstance());

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
        // Handle item selection
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
