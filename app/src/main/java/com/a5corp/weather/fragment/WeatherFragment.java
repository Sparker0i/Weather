package com.a5corp.weather.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.a5corp.weather.GlobalActivity;
import com.a5corp.weather.R;
import com.a5corp.weather.activity.FirstLaunch;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.internet.CheckConnection;
import com.a5corp.weather.internet.FetchWeather;
import com.a5corp.weather.model.Info;
import com.a5corp.weather.model.Log;
import com.a5corp.weather.model.Snack;
import com.a5corp.weather.model.WeatherFort;
import com.a5corp.weather.model.WeatherInfo;
import com.a5corp.weather.permissions.GPSTracker;
import com.a5corp.weather.permissions.Permissions;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.service.NotificationService;
import com.a5corp.weather.utils.Constants;
import com.a5corp.weather.utils.Utils;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.jorgecastilloprz.FABProgressCircle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static com.a5corp.weather.utils.Constants.DESCRIBABLE_KEY;

public class WeatherFragment extends Fragment {
    Typeface weatherFont;
    @BindView(R.id.button1) TextView button;
    @BindView(R.id.weather_icon11) TextView weatherIcon;
    @BindView(R.id.wind_view) TextView windView;
    @BindView(R.id.humidity_view) TextView humidityView;
    @BindView(R.id.direction_view) TextView directionView;
    @BindView(R.id.daily_view) TextView dailyView;
    @BindView(R.id.updated_field) TextView updatedField;
    @BindView(R.id.city_field) TextView cityField;
    @BindView(R.id.sunrise_view) TextView sunriseView;
    @BindView(R.id.sunset_view) TextView sunsetView;
    @BindView(R.id.sunrise_icon) TextView sunriseIcon;
    @BindView(R.id.sunset_icon) TextView sunsetIcon;
    @BindView(R.id.humidity_icon) TextView humidityIcon;
    @BindView(R.id.horizontal_recycler_view) RecyclerView horizontalRecyclerView;
    LinearLayoutManager horizontalLayoutManager;
    double tc;
    Handler handler;
    BottomSheetDialogFragment bottomSheetDialogFragment;
    WeatherInfo json0;
    WeatherFort json1;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeView;
    FloatingActionButton fab;
    FABProgressCircle fabProgressCircle;

    CheckConnection cc;
    Info json;
    String citys = null;
    MaterialDialog pd;
    FetchWeather wt;
    Prefs preferences;
    GPSTracker gps;
    View rootView;
    Permissions permission;

    public WeatherFragment() {
        handler = new Handler();
    }

    public WeatherFragment setCity(String city) {
        this.citys = city;
        return this;
    }

    public String getCity() {
        return citys;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, rootView);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this.activity())
                .title(getString(R.string.please_wait))
                .content(getString(R.string.loading))
                .cancelable(false)
                .progress(true, 0);
        pd = builder.build();
        setHasOptionsMenu(true);
        preferences = new Prefs(context());
        weatherFont = Typeface.createFromAsset(activity().getAssets(), "fonts/weather-icons-v2.0.10.ttf");
        fab = ((WeatherActivity) activity()).findViewById(R.id.fab);
        Bundle bundle = getArguments();
        fabProgressCircle = ((WeatherActivity) activity()).findViewById(R.id.fabProgressCircle);
        int mode;
        if (bundle != null)
            mode = bundle.getInt(Constants.MODE, 0);
        else
            mode = 0;
        if (mode == 0)
            updateWeatherData(preferences.getCity(), null, null);
        else
            updateWeatherData(null, Float.toString(preferences.getLatitude()), Float.toString(preferences.getLongitude()));
        gps = new GPSTracker(context());
        cityField.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        updatedField.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        humidityView.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        sunriseIcon.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        sunriseIcon.setTypeface(weatherFont);
        sunriseIcon.setText(activity().getString(R.string.sunrise_icon));
        sunsetIcon.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        sunsetIcon.setTypeface(weatherFont);
        sunsetIcon.setText(activity().getString(R.string.sunset_icon));
        humidityIcon.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        humidityIcon.setTypeface(weatherFont);
        humidityIcon.setText(activity().getString(R.string.humidity_icon));
        windView.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        swipeView.setColorSchemeResources(R.color.red, R.color.green, R.color.blue, R.color.yellow, R.color.orange);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changeCity(preferences.getCity());
                        swipeView.setRefreshing(false);
                    }
                });
            }
        });
        horizontalLayoutManager
                = new LinearLayoutManager(context(), LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        horizontalRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (horizontalLayoutManager.findLastVisibleItemPosition() == 9 || citys != null)
                    fab.hide();
                else
                    fab.show();
            }
        });

        directionView.setTypeface(weatherFont);
        directionView.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        dailyView.setText(getString(R.string.daily));
        dailyView.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        sunriseView.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        sunsetView.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        button.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        pd.show();
        horizontalRecyclerView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        weatherIcon.setTypeface(weatherFont);
        weatherIcon.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        if (citys == null)
            ((WeatherActivity) activity()).showFab();
        else
            ((WeatherActivity) activity()).hideFab();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (citys == null)
            activity().getMenuInflater().inflate(R.menu.menu_weather, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.location:
                permission = new Permissions(context());
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.READ_COARSE_LOCATION);
                break;
            case R.id.share:
                shareClicked();
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            cc = new CheckConnection(context());
            if (!cc.isNetworkAvailable())
                showNoInternet();
            else {
                pd.show();
                updateWeatherData(preferences.getCity(), null, null);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }

    private void updateWeatherData(final String city, final String lat, final String lon) {
        if (getArguments() != null && getArguments().getSerializable(Constants.SPLASH_DATA) != null) {
            Log.i("message","has json");
            handler.post(new Runnable() {
                public void run() {
                    json = (Info) getArguments().getSerializable(Constants.SPLASH_DATA);
                    preferences.setLaunched();
                    renderWeather(json);
                    if (!preferences.getv3TargetShown())
                        showTargets();
                    if (pd.isShowing())
                        pd.dismiss();
                    if (citys == null) {
                        preferences.setLastCity(json.day.getName() + "," + json.day.getSys().getCountry());
                        ((WeatherActivity) activity()).createShortcuts();
                    } else
                        preferences.setLastCity(preferences.getLastCity());
                    NotificationService.enqueueWork(context(), new Intent(context(), WeatherActivity.class));
                }
            });
        } else {
            Log.i("message","has to load json");
            wt = new FetchWeather(context());
            if(citys==null){
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fabProgressCircle.show();
                    }
                },50);
            }
            try {
                if (lat == null && lon == null) {
                    json = wt.execute(citys != null ? citys : city).get();
                } else if (city == null) {
                    json = wt.execute(lat, lon).get();
                    Log.i("message",lat+lon);
                }
            } catch (InterruptedException iex) {
                Log.e("InterruptedException", "iex");
            } catch (ExecutionException eex) {
                Log.e("ExecutionException", "eex");
            }
            if (pd.isShowing())
                pd.dismiss();
            if (json == null) {
                preferences.setCity(preferences.getLastCity());
                handler.post(new Runnable() {
                    public void run() {
                        GlobalActivity.i = 1;
                        if (!preferences.getLaunched()) {
                            FirstStart();
                        } else {
                            if (citys == null)
                                fabProgressCircle.hide();
                            cc = new CheckConnection(context());
                            if (!cc.isNetworkAvailable()) {
                                showNoInternet();
                            } else {
                                if (pd.isShowing())
                                    pd.dismiss();
                                showInputDialog();
                            }
                        }
                    }
                });
            } else {
                handler.post(new Runnable() {
                    public void run() {
                        preferences.setLaunched();
                        renderWeather(json);
                        if (!preferences.getv3TargetShown())
                            showTargets();
                        if (pd.isShowing())
                            pd.dismiss();
                        if (citys == null) {
                            preferences.setLastCity(json.day.getName() + "," + json.day.getSys().getCountry());
                            ((WeatherActivity) getActivity()).createShortcuts();
                            progress();
                        } else
                            preferences.setLastCity(preferences.getLastCity());
                        NotificationService.enqueueWork(context(), new Intent(context(), WeatherActivity.class));
                    }
                });
            }
        }
    }


    private void progress() {
        fabProgressCircle.onArcAnimationComplete();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fabProgressCircle.hide();
            }
        }, 500);
    }

    public void FirstStart() {
        if (pd.isShowing())
            pd.dismiss();
        Intent intent = new Intent(activity(), FirstLaunch.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public List<WeatherFort.WeatherList> getDailyJson() {
        return json.fort.getList();
    }

    public void changeCity(String city) {
        updateWeatherData(city, null, null);
        preferences.setCity(city);
    }

    public void changeCity(String lat, String lon) {
        ((WeatherActivity) activity()).showFab();
        updateWeatherData(null, lat, lon);
    }

    private Context context() {
        return getContext();
    }

    private FragmentActivity activity() {
        return getActivity();
    }

    private void showInputDialog() {
        new MaterialDialog.Builder(activity())
                .title(getString(R.string.change_city))
                .content(getString(R.string.could_not_find))
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
                        changeCity(input.toString());
                    }
                })
                .cancelable(false)
                .show();
    }

    private void showTargets() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new MaterialTapTargetPrompt.Builder(activity())
                        .setTarget(R.id.fab)
                        .setBackgroundColour(ContextCompat.getColor(getActivity(), R.color.md_light_blue_400))
                        .setFocalColour(ContextCompat.getColor(getActivity(), R.color.colorAccent))
                        .setPrimaryText(getString(R.string.target_search_title))
                        .setSecondaryText(getString(R.string.target_search_content))
                        .setIconDrawableColourFilter(ContextCompat.getColor(activity(), R.color.md_black_1000))
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                    showRefresh();
                            }
                        })
                        .show();
            }
        }, 4500);
    }

    private void showLocTarget() {
        new MaterialTapTargetPrompt.Builder(activity())
                .setTarget(R.id.location)
                .setBackgroundColour(ContextCompat.getColor(context(), R.color.md_light_blue_400))
                .setPrimaryText(getString(R.string.location))
                .setFocalColour(ContextCompat.getColor(context(), R.color.colorAccent))
                .setSecondaryText(getString(R.string.target_location_content))
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                            preferences.setv3TargetShown(true);
                    }
                })
                .show();
    }

    private void showRefresh() {
        new MaterialTapTargetPrompt.Builder(activity())
                .setTarget(R.id.toolbar)
                .setBackgroundColour(ContextCompat.getColor(context(), R.color.md_light_blue_400))
                .setPrimaryText(getString(R.string.target_refresh_title))
                .setFocalColour(ContextCompat.getColor(context(), R.color.colorAccent))
                .setSecondaryText(getString(R.string.target_refresh_content))
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                            showLocTarget();
                    }
                })
                .show();
    }

    public void showNoInternet() {
        new MaterialDialog.Builder(context())
                .title(getString(R.string.no_internet_title))
                .cancelable(false)
                .content(getString(R.string.no_internet_content))
                .positiveText(getString(R.string.no_internet_mobile_data))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                        startActivityForResult(intent, 0);
                    }
                })
                .negativeText(getString(R.string.no_internet_wifi))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.READ_COARSE_LOCATION: {
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

    private void showCity() {
        gps = new GPSTracker(activity());
        if (!gps.canGetLocation())
            gps.showSettingsAlert();
        else {
            String lat = gps.getLatitude();
            String lon = gps.getLongitude();
            if(getArguments()!=null){
                Bundle bundle=getArguments();
                bundle.putSerializable(Constants.SPLASH_DATA,null);
                getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment).setArguments(bundle);
            }
            changeCity(lat, lon);
        }
    }

    private void renderWeather(Info jsonObj) {
        try {
            json0 = jsonObj.day;
            json1 = jsonObj.fort;
            tc = json0.getMain().getTemp();
            preferences.setLatitude((float) json1.getCity().getCoord().getLatitude());
            preferences.setLongitude((float) json1.getCity().getCoord().getLongitude());
            if (citys == null)
                preferences.setCity(json1.getCity().getName() + "," + json0.getSys().getCountry());
            int a = (int) Math.round(json0.getMain().getTemp());
            final String city = json1.getCity().getName().toUpperCase(Locale.US) +
                    ", " +
                    json1.getCity().getCountry();
            cityField.setText(city);
            cityField.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Snack.make(v, city, Snackbar.LENGTH_SHORT);
                }
            });
            List<WeatherFort.WeatherList> details = json1.getList();
            for (int i = 0; i < 10; ++i) {
                details.set(i, json1.getList().get(i));
            }
            HorizontalAdapter horizontalAdapter = new HorizontalAdapter(details);
            horizontalRecyclerView.setAdapter(horizontalAdapter);
            boolean timeFormat24Hours = preferences.isTimeFormat24Hours();
            final String d1 = new java.text.SimpleDateFormat(timeFormat24Hours ? "kk:mm" : "hh:mm a", Locale.US).format(new Date(json0.getSys().getSunrise() * 1000));
            final String d2 = new java.text.SimpleDateFormat(timeFormat24Hours ? "kk:mm" : "hh:mm a", Locale.US).format(new Date(json0.getSys().getSunset() * 1000));
            sunriseView.setText(d1);
            sunsetView.setText(d2);
            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = "Last update: " + df.format(new Date(json0.getDt() * 1000));
            updatedField.setText(updatedOn);
            final String humidity = getString(R.string.humidity_, json0.getMain().getHumidity());
            final String humidity1 = getString(R.string.humidity, json0.getMain().getHumidity());
            humidityView.setText(humidity);
            humidityIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snack.make(rootView, humidity1, Snackbar.LENGTH_SHORT);
                }
            });
            humidityView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snack.make(rootView, humidity1, Snackbar.LENGTH_SHORT);
                }
            });
            final String wind = getString(R.string.wind, json0.getWind().getSpeed(), PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.PREF_TEMPERATURE_UNITS, Constants.METRIC).equals(Constants.METRIC) ? getString(R.string.mps) : getString(R.string.mph));
            final String wind1 = getString(R.string.wind_, json0.getWind().getSpeed(), PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.PREF_TEMPERATURE_UNITS, Constants.METRIC).equals(Constants.METRIC) ? getString(R.string.mps) : getString(R.string.mph));
            windView.setText(wind);
            directionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snack.make(rootView, wind1, Snackbar.LENGTH_SHORT);
                }
            });
            windView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snack.make(rootView, wind1, Snackbar.LENGTH_SHORT);
                }
            });
            weatherIcon.setText(Utils.setWeatherIcon(context(), json0.getWeather().get(0).getId()));
            weatherIcon.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        String rs = json0.getWeather().get(0).getDescription();
                        String[] strArray = rs.split(" ");
                        StringBuilder builder = new StringBuilder();
                        for (String s : strArray) {
                            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                            builder.append(cap.concat(" "));
                        }
                        Snack.make(v, getString(R.string.hey_there_condition, builder.toString().substring(0, builder.length() - 1)), Snackbar.LENGTH_SHORT);
                    } catch (Exception e) {
                        Log.e("Error", "Main Weather Icon OnClick Details could not be loaded");
                    }
                }
            });
            String r1 = Integer.toString(a) + "°";
            button.setText(r1);
            int deg = json0.getWind().getDirection();
            setDeg(deg);
        } catch (Exception e) {
            Log.e(WeatherFragment.class.getSimpleName(), "One or more fields not found in the JSON data");
        }
    }

    private void setDeg(int deg) {
        int index = Math.abs(Math.round(deg % 360) / 45);
        switch (index) {
            case 0:
                directionView.setText(activity().getString(R.string.top));
                setDirection(getString(R.string.north));
                break;
            case 1:
                directionView.setText(activity().getString(R.string.top_right));
                setDirection(getString(R.string.north_east));
                break;
            case 2:
                directionView.setText(activity().getString(R.string.right));
                setDirection(getString(R.string.east));
                break;
            case 3:
                directionView.setText(activity().getString(R.string.bottom_right));
                setDirection(getString(R.string.south_east));
                break;
            case 4:
                directionView.setText(activity().getString(R.string.down));
                setDirection(getString(R.string.south));
                break;
            case 5:
                directionView.setText(activity().getString(R.string.bottom_left));
                setDirection(getString(R.string.south_west));
                break;
            case 6:
                directionView.setText(activity().getString(R.string.left));
                setDirection(getString(R.string.west));
                break;
            case 7:
                directionView.setText(activity().getString(R.string.top_left));
                setDirection(getString(R.string.north_west));
                break;
        }
    }

    private void setDirection(final String string) {
        directionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snack.make(view, getString(R.string.wind_blowing_in, string), Snackbar.LENGTH_SHORT);
            }
        });
    }

    public static CustomBottomSheetDialogFragment newInstance(WeatherFort.WeatherList describable) {
        CustomBottomSheetDialogFragment fragment = new CustomBottomSheetDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, describable);
        fragment.setArguments(bundle);

        return fragment;
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {
        private List<WeatherFort.WeatherList> horizontalList;

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView weather_icon, details_view;

            MyViewHolder(View view) {
                super(view);
                weather_icon = view.findViewById(R.id.weather_icon);
                details_view = view.findViewById(R.id.details_view);
            }
        }

        HorizontalAdapter(List<WeatherFort.WeatherList> horizontalList) {
            this.horizontalList = horizontalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.weather_daily_list_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.weather_icon.setText(Utils.setWeatherIcon(context(), horizontalList.get(position).getWeather().get(0).getId()));
            long date1 = horizontalList.get(position).getDt();
            Date expiry = new Date(date1 * 1000);
            String date = new SimpleDateFormat("EE, dd", new Locale(new Prefs(context()).getLanguage())).format(expiry);
            String line2 =
                    horizontalList.get(position).getTemp().getMax() + "°" + "      ";
            String line3 = horizontalList.get(position).getTemp().getMin() + "°";
            String fs = date + "\n" + line2 + line3 + "\n";
            SpannableString ss1 = new SpannableString(fs);
            ss1.setSpan(new RelativeSizeSpan(1.1f), fs.indexOf(date), date.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss1.setSpan(new RelativeSizeSpan(1.4f), fs.indexOf(line2), date.length() + line2.length(), 0);
            holder.details_view.setText(ss1);
            final int pos = position;
            holder.details_view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    bottomSheetDialogFragment = newInstance(horizontalList.get(pos));
                    bottomSheetDialogFragment.show(activity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                }
            });
            holder.weather_icon.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    bottomSheetDialogFragment = newInstance(horizontalList.get(pos));
                    bottomSheetDialogFragment.show(activity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                }
            });
            holder.weather_icon.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
            holder.weather_icon.setTypeface(weatherFont);
            holder.details_view.setTextColor(ContextCompat.getColor(context(), R.color.textColor));
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }

    @Override
    public void onResume() {
        changeCity(preferences.getCity());
        super.onResume();
    }

    public void shareClicked()
    {
        final String city = json1.getCity().getName().toUpperCase(Locale.US) +
                ", " +
                json1.getCity().getCountry();
        int temperature = (int) Math.round(json0.getMain().getTemp());
        String temp_unit = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.PREF_TEMPERATURE_UNITS, Constants.METRIC).equals(Constants.METRIC) ? (char) 0x00B0 +" Celsius" : (char) 0x00B0 + " Fahrenheit";
        final String wind1 = getString(R.string.wind_, json0.getWind().getSpeed(), PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.PREF_TEMPERATURE_UNITS, Constants.METRIC).equals(Constants.METRIC) ? getString(R.string.mps) : getString(R.string.mph));
        final String humidity1 = getString(R.string.humidity, json0.getMain().getHumidity());
        boolean timeFormat24Hours = preferences.isTimeFormat24Hours();
        final String sunriseTime = new java.text.SimpleDateFormat(timeFormat24Hours ? "kk:mm" : "hh:mm a", Locale.US).format(new Date(json0.getSys().getSunrise() * 1000));
        final String sunsetTime = new java.text.SimpleDateFormat(timeFormat24Hours ? "kk:mm" : "hh:mm a", Locale.US).format(new Date(json0.getSys().getSunset() * 1000));
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,"Region : " + city + "\nTemperature : " + temperature + "" + temp_unit + "\n" + wind1 + "\n" + humidity1 + "\nSunrise Time : " + sunriseTime + "\nSunset Time : " + sunsetTime + "\n\n" + "Download Simple Weather today: https://play.google.com/store/apps/details?id=com.a5corp.weather" + "\n");
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);
    }
}


