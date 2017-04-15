package com.a5corp.weather.fragment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.a5corp.weather.preferences.Preferences;

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {

    TextView windIcon , rainIcon , snowIcon , humidityIcon , pressureIcon;
    TextView windText , rainText , snowText , humidityText , pressureText;
    TextView nightValue , mornValue , dayValue , eveValue;
    TextView condition;
    View rootView;
    Preferences preferences;
    Typeface weatherFont;
    WeatherFort.WeatherList json;
    private static final String DESCRIBABLE_KEY = "describable_key";
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_modal , container, false);
        condition = (TextView) rootView.findViewById(R.id.description);
        preferences = new Preferences(getContext());
        nightValue = (TextView) rootView.findViewById(R.id.night_temperature);
        mornValue = (TextView) rootView.findViewById(R.id.morning_temperature);
        dayValue = (TextView) rootView.findViewById(R.id.day_temperature);
        eveValue = (TextView) rootView.findViewById(R.id.evening_temperature);
        windIcon = (TextView) rootView.findViewById(R.id.wind_icon);
        windIcon.setTypeface(weatherFont);
        windIcon.setText(getString(R.string.speed_icon));
        rainIcon = (TextView) rootView.findViewById(R.id.rain_icon);
        rainIcon.setTypeface(weatherFont);
        rainIcon.setText(getString(R.string.rain));
        snowIcon = (TextView) rootView.findViewById(R.id.snow_icon);
        snowIcon.setTypeface(weatherFont);
        snowIcon.setText(getString(R.string.snow));
        humidityIcon = (TextView) rootView.findViewById(R.id.humidity_icon);
        humidityIcon.setTypeface(weatherFont);
        humidityIcon.setText(getString(R.string.humidity_icon));
        pressureIcon = (TextView) rootView.findViewById(R.id.pressure_icon);
        pressureIcon.setTypeface(weatherFont);
        pressureIcon.setText(getString(R.string.pressure_icon));
        windText = (TextView) rootView.findViewById(R.id.wind);
        rainText = (TextView) rootView.findViewById(R.id.rain);
        snowText = (TextView) rootView.findViewById(R.id.snow);
        humidityText = (TextView) rootView.findViewById(R.id.humidity);
        pressureText = (TextView) rootView.findViewById(R.id.pressure);
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
            String wind = "Speed : " + json.getSpeed() + " m/";
            if (preferences.getUnits().equals("imperial"))
                wind = wind + "h";
            else
                wind = wind + "s";
            windText.setText(wind);
            try {
                rainText.setText("Rain : " + json.getRain() + " mm");
            }
            catch (Exception ex) {
                rainText.setText("Rain : 0 mm");
            }
            try {
                snowText.setText("Snow : " + json.getSnow() + " mm");
            }
            catch (Exception ex) {
                snowText.setText("Snow : 0 mm");
            }
            humidityText.setText("Humidity : " + json.getHumidity() + " %");
            pressureText.setText("Pressure : " + json.getPressure() + " hPa");
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