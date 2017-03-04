package com.a5corp.weather.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.internet.CheckConnection;
import com.a5corp.weather.utilities.CustomValueFormatter;
import com.a5corp.weather.utilities.YValueFormatter;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphsFragment extends Fragment {

    private View rootView;

    public GraphsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_graphs, container, false);

        return rootView;
    }
}
