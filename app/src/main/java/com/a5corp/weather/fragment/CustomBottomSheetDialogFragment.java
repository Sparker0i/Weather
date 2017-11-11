package com.a5corp.weather.fragment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a5corp.weather.R;
import com.a5corp.weather.model.WeatherFort;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Constants;

import java.util.Locale;

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {

    TextView windIcon , rainIcon , snowIcon , humidityIcon , pressureIcon;
    TextView windText , rainText , snowText , humidityText , pressureText;
    TextView nightValue , mornValue , dayValue , eveValue;
    TextView condition;
    View rootView;
    Prefs preferences;
    Typeface weatherFont;
    WeatherFort.WeatherList json;
    private static final String DESCRIBABLE_KEY = Constants.DESCRIBABLE_KEY;
    WeatherFort.WeatherList mDescribable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDescribable = (WeatherFort.WeatherList) getArguments().getSerializable(
                DESCRIBABLE_KEY);
        json = mDescribable;
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_modal , container, false);
        condition = rootView.findViewById(R.id.description);
        preferences = new Prefs(getContext());
        nightValue = rootView.findViewById(R.id.night_temperature);
        mornValue = rootView.findViewById(R.id.morning_temperature);
        dayValue = rootView.findViewById(R.id.day_temperature);
        eveValue = rootView.findViewById(R.id.evening_temperature);
        windIcon = rootView.findViewById(R.id.wind_icon);
        windIcon.setTypeface(weatherFont);
        windIcon.setText(getString(R.string.speed_icon));
        rainIcon = rootView.findViewById(R.id.rain_icon);
        rainIcon.setTypeface(weatherFont);
        rainIcon.setText(getString(R.string.rain));
        snowIcon = rootView.findViewById(R.id.snow_icon);
        snowIcon.setTypeface(weatherFont);
        snowIcon.setText(getString(R.string.snow));
        humidityIcon = rootView.findViewById(R.id.humidity_icon);
        humidityIcon.setTypeface(weatherFont);
        humidityIcon.setText(getString(R.string.humidity_icon));
        pressureIcon = rootView.findViewById(R.id.pressure_icon);
        pressureIcon.setTypeface(weatherFont);
        pressureIcon.setText(getString(R.string.pressure_icon));
        windText = rootView.findViewById(R.id.wind);
        rainText = rootView.findViewById(R.id.rain);
        snowText = rootView.findViewById(R.id.snow);
        humidityText = rootView.findViewById(R.id.humidity);
        pressureText = rootView.findViewById(R.id.pressure);
        updateElements();
        return rootView;
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        //super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.dialog_modal, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);

        }
    }

    public void updateElements() {
        setCondition();
        setOthers();
        setTemperatures();
    }

    public void setCondition() {
            String cond = json.getWeather().get(0).getDescription();
            String[] strArray = cond.split(" ");
            final StringBuilder builder = new StringBuilder();
            for (String s : strArray) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap.concat(" "));
            }
            condition.setText(builder.toString());
    }

    public void setOthers() {
        try {
            String wind = String.format(Locale.ENGLISH , getString(R.string.speed) , json.getSpeed());
            if (PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.PREF_TEMPERATURE_UNITS , Constants.METRIC).equals(Constants.IMPERIAL))
                wind = wind + " " + getString(R.string.mph);
            else
                wind = wind + " " + getString(R.string.mps);
            windText.setText(wind);
            try {
                rainText.setText(String.format(Locale.ENGLISH , getString(R.string.rain_) , json.getRain()));
            }
            catch (Exception ex) {
                rainText.setText(String.format(Locale.ENGLISH , getString(R.string.rain_) , 0));
            }
            try {
                snowText.setText(String.format(Locale.ENGLISH , getString(R.string.snow_) , json.getSnow()));
            }
            catch (Exception ex) {
                snowText.setText(String.format(Locale.ENGLISH , getString(R.string.snow_) , 0));
            }
            humidityText.setText(String.format(Locale.ENGLISH , getString(R.string.humidity) , json.getHumidity()));
            pressureText.setText(String.format(Locale.ENGLISH , getString(R.string.pressure) , json.getPressure()));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTemperatures() {
        dayValue.setText(String.format("%s°" , json.getTemp().getDay()));
        mornValue.setText(String.format("%s°" ,json.getTemp().getMorn()));
        eveValue.setText(String.format("%s°" , json.getTemp().getEve()));
        nightValue.setText(String.format("%s°" , json.getTemp().getNight()));
    }
}