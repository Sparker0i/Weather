package com.a5corp.weather.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a5corp.weather.R;
import com.a5corp.weather.internet.FetchWeather;
import com.a5corp.weather.preferences.Preferences;
import com.a5corp.weather.utils.XFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GraphsFragment extends Fragment {

    View rootView;
    Handler handler;
    LineChart chart;
    List<Entry> temperatures;
    LineDataSet dataSet;
    List<Entry> entries = new ArrayList<>();
    FetchWeather fw;
    Preferences pf;
    String[] dates = new String[10];

    public GraphsFragment() {
        // Required empty public constructor
        handler = new Handler();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fw = new FetchWeather(getContext());
            pf = new Preferences(getContext());
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_graphs, container, false);
        Log.i("Loaded" , "Fragment");
        chart = (LineChart) rootView.findViewById(R.id.chart);
        temperatures = new ArrayList<>();
        function();
        return rootView;
    }

    public void function() {
        getTemperatures();
        loadChart();
    }

    public void getTemperatures() {
        Bundle bundle = this.getArguments();
        JSONObject str;
        JSONArray list;
        if (bundle != null) {
            try {
                str = new JSONObject(bundle.getString("json", null));
                list = str.getJSONArray("list");
                for (int i = 0; i < 10; ++i) {
                    long day = list.getJSONObject(i).getLong("dt");
                    long temp = list.getJSONObject(i).getJSONObject("temp").getLong("day");
                    entries.add(new Entry(day , temp));
                    dates[i] = getDay(day);
                    Log.i("Added" , "Entry");
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
                Log.i("Caught" , "JSON Ex");
            }
        }
        else
            Log.e("Null" , "Bundle");
    }

    public void loadChart() {
        dataSet = new LineDataSet(entries, "Temperature"); // add entries to dataset
        dataSet.setColor(Color.parseColor("#FF0000"));
        dataSet.setValueTextColor(Color.parseColor("#FFFFFF"));
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        Description desc = new Description();
        desc.setText("Temperature, " + getString(R.string.c));
        chart.setDescription(desc);
        chart.setBackgroundColor(Color.MAGENTA);

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.enableAxisLineDashedLine(2f , 4f , 2f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(2f);
        xAxis.setValueFormatter(new XFormatter(dates));

        chart.invalidate();
    }

    public String getDay(long dt) {
        dt *= 1000;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(dt));
        switch(c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY :
                return "Sun";
            case Calendar.MONDAY :
                return "Mon";
            case Calendar.TUESDAY :
                return "Tue";
            case Calendar.WEDNESDAY :
                return "Wed";
            case Calendar.THURSDAY :
                return "Thu";
            case Calendar.FRIDAY :
                return "Fri";
            case Calendar.SATURDAY :
                return "Sat";
        }
        return null;
    }
}
