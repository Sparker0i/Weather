package com.a5corp.weather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
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
import com.a5corp.weather.preferences.Prefs;
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

public class WeatherActivity extends AppCompatActivity {
    Prefs preferences;
    WeatherFragment wf;
    GraphsFragment gf;
    MapsFragment mf;
    @BindView(R.id.toolbar) Toolbar toolbar;
    Drawer drawer;
    NotificationManagerCompat mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mManager = NotificationManagerCompat.from(this);
        preferences = new Prefs(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        wf = new WeatherFragment();
        gf = new GraphsFragment();
        mf = new MapsFragment();

        Intent intent = getIntent();
        if (intent != null ) {
            if (savedInstanceState == null) {
                switch (intent.getIntExtra("drawer", 1)) {
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment, wf)
                                .commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment, wf)
                                .commit();
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment, gf)
                                .commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment, mf)
                                .commit();
                        break;
                }
                initDrawer(intent.getIntExtra("drawer", 1));
            }
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, wf)
                    .commit();
            initDrawer(1);
        }
    }

    public void initDrawer(int pos) {
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
                wf = new WeatherFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, wf)
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
                .withSelectedItem(pos)
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
                                        wf = new WeatherFragment();
                                        getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment, wf)
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

    private void showApiKeyBox() {
        new MaterialDialog.Builder(this)
                .title("Change OWM API Key")
                .content("You can enter your own custom OpenWeatherMap API key, and help reduce the load from my API Key\n\n" +
                        "BEWARE: Entering a wrong API Key may result in the app going haywire. Do this at your own risk")
                .neutralText("RESET")
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        preferences.setWeatherKey(Constants.OWM_APP_ID);
                    }
                })
                .negativeText("CANCEL")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog , @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, @NonNull CharSequence input) {
                        preferences.setWeatherKey(input.toString());
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
