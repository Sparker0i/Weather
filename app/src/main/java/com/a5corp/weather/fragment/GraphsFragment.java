package com.a5corp.weather.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.a5corp.weather.R;
import com.a5corp.weather.internet.FetchWeather;
import com.a5corp.weather.preferences.Preferences;
import com.a5corp.weather.utils.CustomFormatter;
import com.a5corp.weather.utils.XFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

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
    List<Entry> entries = new ArrayList<>();
    FetchWeather fw;
    Preferences pf;
    Bundle bundle;
    CustomFormatter mValueFormatter;
    String[] dates = new String[10];

    public GraphsFragment() {
        handler = new Handler();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mValueFormatter = new CustomFormatter();
        fw = new FetchWeather(getContext());
            pf = new Preferences(getContext());
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_graph, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggle_values :
                toggleValues();
                break;
        }
        return true;
    }

    public void function() {
        getTemperatures();
        loadChart();
    }

    public void toggleValues() {
        for (IDataSet set : chart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
            set.setValueTextColor(Color.WHITE);
        }
        chart.invalidate();
    }

    public void getTemperatures() {
        bundle = this.getArguments();
        if (bundle != null) {
            createEntries();
        }
        else
            Log.e("Null" , "Bundle");
    }

    public void loadChart() {
        chart.setDrawGridBackground(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setMaxHighlightDistance(300);
        chart.setPinchZoom(true);
        chart.setPadding(2 , 2 , 2 , 2);
        chart.getLegend().setEnabled(true);
        chart.getLegend().setTextColor(Color.WHITE);
        chart.setBackgroundColor(Color.parseColor("#000000"));

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.setTextColor(Color.WHITE);
        yAxisRight.enableAxisLineDashedLine(2f , 4f , 2f);

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setTextColor(Color.WHITE);

        XAxis x = chart.getXAxis();
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setTextColor(Color.parseColor("#FFFFFF"));
        x.setValueFormatter(new XFormatter(dates));

        LineDataSet set;
        if (chart.getData() != null) {
            chart.getData().removeDataSet(chart.getData().getDataSetByIndex(
                    chart.getData().getDataSetCount() - 1));
            chart.getLegend().setTextColor(Color.parseColor("#FFFFFF"));
        }
        set = new LineDataSet(entries, "Temperature, " + getString(R.string.c));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f);
        set.setDrawCircles(false);
        set.setLineWidth(2f);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        set.setColor(Color.MAGENTA);
        set.setHighlightEnabled(false);
        set.setValueFormatter(mValueFormatter);

        LineData data = new LineData(set);
        chart.setData(data);

        chart.invalidate();
    }

    public void createEntries() {
        JSONObject str;
        JSONArray list;
        try {
            str = new JSONObject(bundle.getString("json", null));
            list = str.getJSONArray("list");
            for (int i = 0; i < 10; ++i) {
                long day = list.getJSONObject(i).getLong("dt");
                long temp = list.getJSONObject(i).getJSONObject("temp").getLong("day");
                entries.add(new Entry(i , temp));
                Log.i("Added" , "Entry : " + i + " " + (int) temp);
                dates[i] = getDay(day);
                Log.i("Added" , "Day : " + dates[i]);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            Log.i("Caught" , "JSON Ex");
        }
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
