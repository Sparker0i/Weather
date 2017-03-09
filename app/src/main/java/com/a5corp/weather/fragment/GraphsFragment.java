package com.a5corp.weather.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a5corp.weather.R;
import com.a5corp.weather.internet.CheckConnection;
import com.a5corp.weather.model.WeatherForecast;
import com.a5corp.weather.preferences.Preferences;
import com.a5corp.weather.utilities.CustomFormatter;
import com.a5corp.weather.utilities.YFormatter;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mikephil.charting.charts.LineChart;

import java.util.Date;
import java.util.List;

public class GraphsFragment extends Fragment {

    View rootView;
    Handler handler;
    LineChart chart;
    Temperature[] temperatures;

    public GraphsFragment() {
        // Required empty public constructor
        handler = new Handler();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_graphs, container, false);
        Log.i("Loaded" , "Fragment");
        chart = (LineChart) rootView.findViewById(R.id.chart);
        return rootView;
    }
}

class Temperature {
    private Date date;
    private long temp;

    public Temperature(long str , long temp) {
        this.date = new Date(str * 1000);
        this.temp = temp;
    }

    public Date getDate() {
        return date;
    }

    public long getTemp() {
        return temp;
    }
}