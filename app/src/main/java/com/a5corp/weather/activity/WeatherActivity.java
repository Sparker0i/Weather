package com.a5corp.weather.activity;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;

import com.a5corp.weather.BuildConfig;
import com.a5corp.weather.R;
import com.a5corp.weather.fragment.GraphsFragment;
import com.a5corp.weather.fragment.MapsFragment;
import com.a5corp.weather.fragment.WeatherFragment;
import com.a5corp.weather.model.WeatherFort;
import com.a5corp.weather.preferences.Preferences;
import com.a5corp.weather.service.AlarmTriggerService;
import com.a5corp.weather.utils.Constants;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondarySwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.weather_icons_typeface_library.WeatherIcons;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherActivity extends AppCompatActivity {
    Preferences preferences;

    @BindView(R.id.fab) FloatingActionButton fab;
    @OnClick(R.id.fab) void fabClick() {
        fab.hide();
        showInputDialog();
    }
    WeatherFragment wf;
    Toolbar toolbar;
    Drawer drawer;
    NotificationManagerCompat mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mManager = NotificationManagerCompat.from(this);
        preferences = new Preferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        wf = new WeatherFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, wf)
                    .commit();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab.hide();
        initDrawer();
    }

    public void hideFab() {
        fab.hide();
    }

    public void showFab() {
        fab.show();
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public void initDrawer() {
        final Context context = this;
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
                .withProfileImagesClickable(false)
                .build();
        SecondaryDrawerItem item1 = new SecondaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home)
                .withIcon(new IconicsDrawable(this)
                        .icon(WeatherIcons.Icon.wic_day_sunny));
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_graph)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_trending_up));
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_map)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_map));
        SecondaryDrawerItem item7 = new SecondaryDrawerItem().withIdentifier(7).withName(R.string.drawer_item_about)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_info))
                .withSelectable(false);
        SecondaryDrawerItem item6 = new SecondaryDrawerItem().withIdentifier(6).withName(getString(R.string.drawer_item_custom_key))
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_create))
                .withSelectable(false);
        SecondarySwitchDrawerItem item4 = new SecondarySwitchDrawerItem().withIdentifier(4).withName(getString(R.string.drawer_item_fahrenheit))
                .withChecked(preferences.getUnits().equals("imperial"))
                .withIcon(new IconicsDrawable(this)
                        .icon(WeatherIcons.Icon.wic_fahrenheit))
                .withSelectable(false);
        SecondarySwitchDrawerItem item5 = new SecondarySwitchDrawerItem().withIdentifier(5).withName(getString(R.string.drawer_item_notifications))
                .withChecked(preferences.getNotifs())
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_notifications))
                .withSelectable(false);
        item4.withOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    preferences.setUnits("imperial");
                }
                else {
                    preferences.setUnits("metric");
                }
                drawer.closeDrawer();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, new WeatherFragment())
                        .commit();
            }
        });
        item5.withOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    preferences.setNotifs(true);
                    startService(new Intent(context , AlarmTriggerService.class));
                }
                else {
                    preferences.setNotifs(false);
                    stopService(new Intent(context , AlarmTriggerService.class));
                    mManager.cancelAll();
                }
            }
        });
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSelectedItem(1)
                .withTranslucentStatusBar(true)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        new DividerDrawerItem(),
                        item4,
                        item5,
                        new DividerDrawerItem(),
                        item6,
                        item7
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                            if (drawerItem != null) {
                                switch((int) drawerItem.getIdentifier()) {
                                    case 1:
                                        getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment, new WeatherFragment())
                                                .commit();
                                        break;
                                    case 2:
                                        GraphsFragment graphsFragment = newGraphInstance(new ArrayList<>(wf.getDailyJson()));
                                        getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment, graphsFragment)
                                                .commit();
                                        break;
                                    case 3:
                                        MapsFragment mapsFragment = new MapsFragment();
                                        getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment, mapsFragment)
                                                .commit();
                                        break;
                                    case 6:
                                        showApiKeyBox();
                                        break;
                                    case 7:
                                        startActivity(new Intent(WeatherActivity.this, AboutActivity.class));
                                        break;
                                }
                            }
                        return false;
                    }
                })
                .build();
    }

    public ActionBarDrawerToggle getToggle() {
        return drawer.getActionBarDrawerToggle();
    }

    private void showApiKeyBox() {
        new MaterialDialog.Builder(this)
                .title("Change OWM API Key")
                .content("You can enter your own custom OpenWeatherMap API key, and help reduce the load from my API Key\n\n" +
                        "BEWARE: Entering a wrong API Key may result in a weird behaviour of the app. Do this at your own risk")
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
                .neutralText("RESET")
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        preferences.setWeatherKey(Constants.OWM_APP_ID);
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
                        preferences.setWeatherKey(input.toString());
                        fab.show();
                    }
                }).show();
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

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private static final String DESCRIBABLE_KEY = "describable_key";

    public static GraphsFragment newGraphInstance(ArrayList<WeatherFort.WeatherList> describable) {
        GraphsFragment fragment = new GraphsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, describable);
        fragment.setArguments(bundle);

        return fragment;
    }
}
