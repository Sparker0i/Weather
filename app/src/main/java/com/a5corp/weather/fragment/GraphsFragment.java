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
import java.util.List;

public class GraphsFragment extends Fragment {

    View rootView;
    LineChart chart;
    public List<WeatherForecast> mForecastList;
    CheckConnection mCheckConnection;
    String[] dateArray;
    CustomFormatter customFormatter;
    YFormatter yFormatter;
    Handler handler;
    Preferences preferences;


    public GraphsFragment() {
        // Required empty public constructor
        handler = new Handler();
    }

    private void chartEntry() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Loaded" , "Fragment");
        rootView = inflater.inflate(R.layout.fragment_graphs, container, false);
        customFormatter = new CustomFormatter();
        preferences = new Preferences(getContext());
        yFormatter = new YFormatter();
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this.getActivity())
                .title("Please Wait")
                .content("Loading")
                .progress(true , 0);
        builder.build().show();
    }
}
