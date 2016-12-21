package com.a5corp.weather;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    Typeface weatherFont;
    TextView cityField;
    TextView dateText , tempText, maxText , minText, humidityText, sunriseText, sunsetText, speedText, pressureText;
    TextView weatherIcon, humidityIcon, sunriseIcon, sunsetIcon, speedIcon, pressureIcon;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        cityField = (TextView) rootView.findViewById(R.id.city_field);
        dateText = (TextView) rootView.findViewById(R.id.date_text);
        maxText = (TextView) rootView.findViewById(R.id.max_text);
        minText = (TextView) rootView.findViewById(R.id.min_text);

        weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);
        tempText = (TextView) rootView.findViewById(R.id.temp_text);

        humidityIcon = (TextView) rootView.findViewById(R.id.humidity_icon);
        humidityText = (TextView) rootView.findViewById(R.id.humidity_text);
        speedIcon = (TextView) rootView.findViewById(R.id.speed_icon);
        speedText = (TextView) rootView.findViewById(R.id.speed_text);
        pressureIcon = (TextView) rootView.findViewById(R.id.pressure_icon);
        pressureText = (TextView) rootView.findViewById(R.id.pressure_text);

        sunriseIcon = (TextView) rootView.findViewById(R.id.sunrise_icon);
        sunriseText = (TextView) rootView.findViewById(R.id.sunrise_text);
        sunsetIcon = (TextView) rootView.findViewById(R.id.sunset_icon);
        sunsetText = (TextView) rootView.findViewById(R.id.sunset_text);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
    }
}
