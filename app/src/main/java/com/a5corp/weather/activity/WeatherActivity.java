package com.a5corp.weather.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.a5corp.weather.fragment.MapsFragment;
import com.a5corp.weather.model.Log;
import android.view.View;

import com.a5corp.weather.BuildConfig;
import com.a5corp.weather.R;
import com.a5corp.weather.activity.settings.SettingsActivity;
import com.a5corp.weather.app.MyContextWrapper;
import com.a5corp.weather.fragment.GraphsFragment;
import com.a5corp.weather.fragment.WeatherFragment;
import com.a5corp.weather.internet.FetchWeather;
import com.a5corp.weather.model.Info;
import com.a5corp.weather.model.WeatherFort;
import com.a5corp.weather.preferences.DBHelper;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.service.NotificationService;
import com.a5corp.weather.utils.Constants;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.jorgecastilloprz.FABProgressCircle;
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
import com.mikepenz.weather_icons_typeface_library.WeatherIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import shortbread.Shortbread;
import shortbread.Shortcut;

public class WeatherActivity extends AppCompatActivity {
    Prefs preferences;
    WeatherFragment wf;
    GraphsFragment gf;
    //MapsFragment mf;
    Toolbar toolbar;
    Info jsonLoadedEarlier,json;
    Drawer drawer;
    NotificationManagerCompat mManager;
    Handler handler;
    FloatingActionButton fab;
    DBHelper dbHelper;
    Fragment f;

    int mode = 0;

    @Shortcut(id = "home", icon = R.drawable.shortcut_home, shortLabel = "Home", rank = 2)
    public void addWeather() {

    }

    @Shortcut(id = "graphs", icon = R.drawable.shortcut_graph, shortLabel = "Weather Graphs", rank = 1)
    public void addGraphs() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawer.setSelectionAtPosition(2);
                GraphsFragment graphsFragment = newGraphInstance(new ArrayList<>(wf.getDailyJson()));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, graphsFragment)
                        .commit();
            }
        }, 750);
    }

    /*@Shortcut(id = "maps", icon = R.drawable.shortcut_map, shortLabel = "Weather Maps")
    public void addMaps() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawer.setSelectionAtPosition(3);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, mf)
                        .commit();
            }
        }, 750);
    }*/

    public void hideFab() {
        fab.hide();
        findViewById(R.id.fabProgressCircle).setVisibility(View.INVISIBLE);
        ((FABProgressCircle) findViewById(R.id.fabProgressCircle)).hide();
    }

    public void showFab() {
        fab.show();
        findViewById(R.id.fabProgressCircle).setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        } , 500);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = MyContextWrapper.wrap(newBase, new Prefs(newBase).getLanguage());
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.i("Activity", WeatherActivity.class.getSimpleName());
        mManager = NotificationManagerCompat.from(this);
        preferences = new Prefs(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setClickable(true);
        fab.setOnClickListener(fabClickListener);
        Intent intent = getIntent();
        handler = new Handler();
        fab.show();
        wf = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("mode", intent.getIntExtra(Constants.MODE, 0));
        if(intent.getSerializableExtra(Constants.SPLASH_DATA)!=null){
            jsonLoadedEarlier =(Info)intent.getSerializableExtra(Constants.SPLASH_DATA);
            bundle.putSerializable(Constants.SPLASH_DATA, jsonLoadedEarlier);
        }
        wf.setArguments(bundle);
        gf = new GraphsFragment();
        //mf = new MapsFragment();
        dbHelper = new DBHelper(this);
        initDrawer();
        NotificationService.enqueueWork(this, new Intent(this, WeatherActivity.class));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, wf)
                .commit();
    }

    View.OnClickListener fabClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i("message","fab clicked!");
            hideFab();
            showInputDialog();
        }
    };
    private void showInputDialog() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.change_city))
                .content(getString(R.string.enter_zip_code))
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
                .negativeText(getString(android.R.string.cancel))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        showFab();
                    }
                })
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, @NonNull CharSequence input) {
                        changeCity(input.toString());
                        showFab();
                    }
                }).show();
    }

    private void showCityDialog() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.drawer_item_add_city))
                .content(getString(R.string.pref_add_city_content))
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .negativeText(getString(android.R.string.cancel))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, @NonNull CharSequence input) {
                        checkForCity(input.toString());
                    }
                }).show();
    }

    int i = 5;

    private void checkForCity(final String city) {

        final FetchWeather wt = new FetchWeather(this);
        final Context context = this;
        new Thread() {
            @Override
            public void run() {
                try {
                    json = wt.execute(city).get();
                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                }
                if (json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            new MaterialDialog.Builder(context)
                                    .title(getString(R.string.city_not_found))
                                    .content(getString(R.string.city_not_found))
                                    .onAny(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .negativeText(getString(android.R.string.ok))
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                        }
                                    });
                        }
                    });
                } else {
                        if (dbHelper.cityExists(json.day.getName() + "," + json.day.getSys().getCountry())) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new MaterialDialog.Builder(context)
                                            .title(getString(R.string.city_already_exists))
                                            .content(getString(R.string.need_not_add))
                                            .negativeText(getString(android.R.string.ok))
                                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .show();
                                }
                            });
                        }
                        else {
                            dbHelper.addCity(json.day.getName() + "," + json.day.getSys().getCountry());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SecondaryDrawerItem itemx = new SecondaryDrawerItem().withName(json.day.getName() + "," + json.day.getSys().getCountry())
                                            .withIcon(new IconicsDrawable(context)
                                                    .icon(GoogleMaterial.Icon.gmd_place))
                                            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                                @Override
                                                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                                    if (!(f instanceof WeatherFragment)) {
                                                        Log.i("message","onItemClick");
                                                        wf = new WeatherFragment().setCity(json.day.getName() + "," + json.day.getSys().getCountry());
                                                        getSupportFragmentManager().beginTransaction()
                                                                .replace(R.id.fragment, wf)
                                                                .commit();
                                                    }
                                                    return true;
                                                }
                                            });
                                    drawer.addItemAtPosition(itemx, ++i);
                                }
                            });
                        }
                }
            }
        }.start();
    }

    public void createShortcuts() {
        if (mode == 0) {
            Shortbread.create(this);
            mode = -1;
        }
    }

    public void changeCity(String city){
        jsonLoadedEarlier =null;
        WeatherFragment wf = (WeatherFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        Bundle bundle=wf.getArguments();
        bundle.putSerializable(Constants.SPLASH_DATA,null);
        wf.setArguments(bundle);
        wf.changeCity(city);
        new Prefs(this).setCity(city);
    }

    public void initDrawer() {
        final IProfile profile = new ProfileDrawerItem().withName(getString(R.string.app_name))
                .withEmail("Version : " + BuildConfig.VERSION_NAME)
                .withIcon(R.mipmap.ic_launcher_x);
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
        SecondaryDrawerItem item1 = new SecondaryDrawerItem().withName(R.string.drawer_item_home)
                .withIcon(new IconicsDrawable(this)
                        .icon(WeatherIcons.Icon.wic_day_sunny))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        wf = new WeatherFragment();
                        Bundle bundle=new Bundle();
                        if(jsonLoadedEarlier !=null){
                            bundle.putSerializable(Constants.SPLASH_DATA, jsonLoadedEarlier);
                        }
                        wf.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment, wf)
                                .commit();
                        return true;
                    }
                });
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withName(R.string.drawer_item_graph)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_trending_up))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (!(f instanceof GraphsFragment)) {
                            GraphsFragment graphsFragment = newGraphInstance(new ArrayList<>(wf.getDailyJson()));
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment, graphsFragment)
                                    .commit();
                        }
                        return true;
                    }
                });
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withName(R.string.drawer_item_map)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_map))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (!(f instanceof MapsFragment)) {
                            MapsFragment mapsFragment = new MapsFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment, mapsFragment)
                                    .commit();
                        }
                        return true;
                    }
                });
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withName(R.string.drawer_item_add_city)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_add_location))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        showCityDialog();
                        return true;
                    }
                })
                .withSelectable(false);
        SecondaryDrawerItem item8 = new SecondaryDrawerItem().withName(R.string.drawer_item_about)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_info))
                .withSelectable(false)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        startActivity(new Intent(WeatherActivity.this, AboutActivity.class));
                        return true;
                    }
                });
        SecondaryDrawerItem item9 = new SecondaryDrawerItem().withName(R.string.settings)
                .withIcon(new IconicsDrawable(this)
                        .icon(GoogleMaterial.Icon.gmd_settings))
                .withSelectable(false)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        startActivity(new Intent(WeatherActivity.this, SettingsActivity.class));
                        return true;
                    }
                });
        DrawerBuilder drawerBuilder = new DrawerBuilder();
        drawerBuilder
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        new DividerDrawerItem(),
                        item4
                )
                .addStickyDrawerItems(
                        item8,
                        item9);
        List<String> cities = dbHelper.getCities();
        final ListIterator<String> listIterator = cities.listIterator(cities.size());
        while (listIterator.hasPrevious()) {
            final String city = listIterator.previous();
            drawerBuilder.addDrawerItems(new SecondaryDrawerItem().withName(city)
                    .withIcon(new IconicsDrawable(this)
                            .icon(GoogleMaterial.Icon.gmd_place))
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            wf = new WeatherFragment().setCity(city);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment, wf)
                                    .commit();
                            return true;
                        }
                    })
            );
        }
        drawer = drawerBuilder.build();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        }
        else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
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
