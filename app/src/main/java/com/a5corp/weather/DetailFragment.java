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
        
        return rootView;
    }
}
