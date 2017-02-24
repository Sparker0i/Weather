package com.a5corp.weather.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.a5corp.weather.GlobalActivity;
import com.a5corp.weather.R;
import com.a5corp.weather.fragment.WeatherFragment;
import com.a5corp.weather.permissions.GPSTracker;
import com.a5corp.weather.permissions.Permissions;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.DrawerBuilder;

public class WeatherActivity extends AppCompatActivity {

    Permissions permission;
    String lat, lon;
    GPSTracker gps;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabClick();
            }
        });
        new DrawerBuilder().withActivity(this).build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[],@NonNull int[] grantResults) {
        switch (requestCode) {
            case 20: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCity();
                } else {
                    permission.permission_denied();
                }
                break;
            }
        }
    }

    private void fabClick() {
        fab.hide();
        showInputDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_city : showInputDialog();
                break;
            case R.id.about_page : Intent intent = new Intent(WeatherActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.refresh : changeCity(GlobalActivity.cp.getCity());
                break;
            case R.id.location :
                permission = new Permissions(this);
                permission.checkPermission();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showInputDialog() {
        new MaterialDialog.Builder(this)
                .title("Change City")
                .content("You can change the city by entering City name or the ZIP Code")
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        fab.show();
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        fab.show();
                    }
                })
                .negativeText("CANCEL")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog , @NonNull DialogAction which) {
                        dialog.dismiss();
                        fab.show();
                    }
                })
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, @NonNull CharSequence input) {
                        changeCity(input.toString());
                        fab.show();
                    }
                }).show();
    }

    public void changeCity(String city){
        WeatherFragment wf = (WeatherFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        GlobalActivity.cp.setCity(city);
    }

    public void changeCity(String lat, String lon) {
        WeatherFragment wf = (WeatherFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        wf.changeCity(lat , lon);
    }

    private void showCity() {
        gps = new GPSTracker(this);
        if (!gps.canGetLocation())
            new MaterialDialog.Builder(this)
                    .content("GPS Needs to be enabled to view Weather Data of your Location")
                    .title("Enable GPS")
                    .positiveText("ENABLE")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .show();
        else {
            lat = gps.getLatitude();
            lon = gps.getLongitude();
            changeCity(lat, lon);
        }
    }
}
