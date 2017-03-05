package com.a5corp.weather.activity;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.a5corp.weather.BuildConfig;
import com.a5corp.weather.GlobalActivity;
import com.a5corp.weather.R;
import com.a5corp.weather.fragment.AboutFragment;
import com.a5corp.weather.fragment.GraphsFragment;
import com.a5corp.weather.fragment.MapsFragment;
import com.a5corp.weather.fragment.WeatherFragment;
import com.a5corp.weather.permissions.GPSTracker;
import com.a5corp.weather.permissions.Permissions;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.readystatesoftware.systembartint.SystemBarTintManager;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#05000000"));
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabClick();
            }
        });
        initDrawer();
    }

    public void initDrawer() {
        final IProfile profile = new ProfileDrawerItem().withName("Simple Weather")
                .withEmail("Version : " + BuildConfig.VERSION_NAME)
                .withIcon(R.drawable.ic_launcher_dark);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTextColor(ContextCompat.getColor(this , R.color.md_amber_400))
                .addProfiles(
                        profile
                )
                .withSelectionListEnabled(false)
                .build();
        SecondaryDrawerItem item1 = new SecondaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_home)
                        .sizeRes(R.dimen.activity_horizontal_margin));
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_graph)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_trending_up)
                        .sizeRes(R.dimen.activity_horizontal_margin));
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_map)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_map)
                        .sizeRes(R.dimen.activity_horizontal_margin));
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_settings)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_settings)
                        .sizeRes(R.dimen.activity_horizontal_margin))
                .withSelectable(false);
        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(getToolbar())
                .withSelectedItem(1)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        new DividerDrawerItem(),
                        item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() == 1) {
                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.fragment, new WeatherFragment())
                                        .commit();
                            }
                            else if (drawerItem.getIdentifier() == 2) {
                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.fragment, new GraphsFragment())
                                        .commit();
                            }
                            else if (drawerItem.getIdentifier() == 3) {
                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.fragment, new MapsFragment())
                                        .commit();
                            }
                            else if (drawerItem.getIdentifier() == 4) {
                                startActivity(new Intent(WeatherActivity.this, AboutActivity.class));
                            }
                        }
                        return false;
                    }
                })
                .build();
    }

    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
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
                    permission.permissionDenied();
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
        wf.changeCity(city);
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

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
