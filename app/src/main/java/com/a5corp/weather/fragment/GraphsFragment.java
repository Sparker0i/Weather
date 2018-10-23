package com.a5corp.weather.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import com.a5corp.weather.model.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.model.WeatherFort;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Constants;
import com.a5corp.weather.utils.CustomFormatter;
import com.a5corp.weather.utils.XFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.a5corp.weather.utils.Constants.DESCRIBABLE_KEY;

@SuppressWarnings("unchecked")
public class GraphsFragment extends Fragment {

    View rootView;
    Handler handler;
    LineChart temperatureChart , rainChart, pressureChart, snowChart, windChart;
    List<Entry> tempEntries = new ArrayList<>() ,
            rainEntries = new ArrayList<>() ,
            pressureEntries = new ArrayList<>() ,
            snowEntries = new ArrayList<>() ,
            windEntries = new ArrayList<>();
    Prefs pf;
    CustomFormatter mValueFormatter;
    String[] dates = new String[10];
    private Menu menu;
    int i = 0;
    private ArrayList<WeatherFort.WeatherList> mDescribable;

    public GraphsFragment() {
        handler = new Handler();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mValueFormatter = new CustomFormatter();
        pf = new Prefs(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_graphs, container, false);
        Log.i("Loaded" , "Fragment");
        startTask();
        ((WeatherActivity) activity()).hideFab();
        return rootView;
    }

    public Context context() {
        return getContext();
    }

    public FragmentActivity activity() {
        return getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        activity().getMenuInflater().inflate(R.menu.menu_graph, menu);
        this.menu = menu;
        this.menu.getItem(0).setIcon(ContextCompat.getDrawable(context() , R.drawable.ic_radio_button_unchecked_white_24dp));
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

    private void startTask() {
        temperatureChart = rootView.findViewById(R.id.temperature_chart);
        rainChart = rootView.findViewById(R.id.rain_chart);
        pressureChart = rootView.findViewById(R.id.pressure_chart);
        snowChart = rootView.findViewById(R.id.snow_chart);
        windChart = rootView.findViewById(R.id.wind_chart);
        mDescribable = (ArrayList<WeatherFort.WeatherList>) getArguments().getSerializable(DESCRIBABLE_KEY);
        function();
    }

    public void function() {
        getTemperatures();
        loadCharts();
    }

    public void toggleValues() {
        if (i == 1) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(context() , R.drawable.ic_radio_button_unchecked_white_24dp));
            i = 0;
        }
        else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(context() , R.drawable.ic_radio_button_checked_white_24dp));
            i = 1;
        }
        for (IDataSet set : temperatureChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
            set.setValueTextColor(Color.WHITE);
        }
        temperatureChart.invalidate();
        for (IDataSet set : rainChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
            set.setValueTextColor(Color.WHITE);
        }
        for (IDataSet set : pressureChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
            set.setValueTextColor(Color.WHITE);
        }
        for (IDataSet set : snowChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
            set.setValueTextColor(Color.WHITE);
        }
        for (IDataSet set : windChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
            set.setValueTextColor(Color.WHITE);
        }
        temperatureChart.invalidate();
        rainChart.invalidate();
        pressureChart.invalidate();
        snowChart.invalidate();
        windChart.invalidate();
    }

    public void getTemperatures() {
        createEntries();
    }

    public void loadCharts() {
        loadTemperatureChart();
        loadRainChart();
        loadPressureChart();
        loadSnowChart();
        loadWindChart();
    }

    public void loadTemperatureChart() {
        temperatureChart.setDrawGridBackground(false);
        temperatureChart.setBackgroundColor(Color.BLACK);
        temperatureChart.setTouchEnabled(true);
        temperatureChart.setDragEnabled(true);
        temperatureChart.setMaxHighlightDistance(300);
        temperatureChart.setPinchZoom(true);
        temperatureChart.setPadding(2 , 2 , 2 , 2);
        temperatureChart.getLegend().setEnabled(true);
        temperatureChart.getLegend().setTextColor(Color.WHITE);

        YAxis yAxisRight = temperatureChart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.setTextColor(Color.WHITE);
        yAxisRight.enableAxisLineDashedLine(2f , 4f , 2f);

        YAxis yAxisLeft = temperatureChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.WHITE);

        XAxis x = temperatureChart.getXAxis();
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setTextColor(Color.parseColor("#FFFFFF"));
        x.setValueFormatter(new XFormatter(dates));

        LineDataSet set;
        if (temperatureChart.getData() != null) {
            temperatureChart.getData().removeDataSet(temperatureChart.getData().getDataSetByIndex(
                    temperatureChart.getData().getDataSetCount() - 1));
            temperatureChart.getLegend().setTextColor(Color.parseColor("#FFFFFF"));
        }
        String temp = getString(R.string.pref_temp_header) + ", " + (PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.PREF_TEMPERATURE_UNITS , Constants.METRIC).equals(Constants.METRIC) ? getString(R.string.c) : getString(R.string.f));
        set = new LineDataSet(tempEntries, temp);
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
        temperatureChart.setData(data);

        temperatureChart.invalidate();
    }

    public void loadRainChart() {
        rainChart.setDrawGridBackground(false);
        rainChart.setBackgroundColor(Color.BLACK);
        rainChart.setTouchEnabled(true);
        rainChart.setDragEnabled(true);
        rainChart.setMaxHighlightDistance(300);
        rainChart.setPinchZoom(true);
        rainChart.setPadding(2 , 2 , 2 , 2);
        rainChart.getLegend().setEnabled(true);
        rainChart.getLegend().setTextColor(Color.WHITE);

        YAxis yAxisRight = rainChart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.setTextColor(Color.WHITE);
        yAxisRight.enableAxisLineDashedLine(2f , 4f , 2f);

        YAxis yAxisLeft = rainChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.WHITE);

        XAxis x = rainChart.getXAxis();
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setTextColor(Color.parseColor("#FFFFFF"));
        x.setValueFormatter(new XFormatter(dates));

        LineDataSet set;
        if (rainChart.getData() != null) {
            rainChart.getData().removeDataSet(rainChart.getData().getDataSetByIndex(
                    rainChart.getData().getDataSetCount() - 1));
            rainChart.getLegend().setTextColor(Color.parseColor("#FFFFFF"));
        }
        set = new LineDataSet(rainEntries, getString(R.string.bottom_rain) + "," + getString(R.string.mm));
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set.setCubicIntensity(0.2f);
        set.setDrawCircles(false);
        set.setLineWidth(2f);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        set.setColor(Color.GREEN);
        set.setHighlightEnabled(false);
        set.setValueFormatter(mValueFormatter);

        LineData data = new LineData(set);
        rainChart.setData(data);

        rainChart.invalidate();
    }

    public void loadPressureChart() {
        pressureChart.setDrawGridBackground(false);
        pressureChart.setBackgroundColor(Color.BLACK);
        pressureChart.setTouchEnabled(true);
        pressureChart.setDragEnabled(true);
        pressureChart.setMaxHighlightDistance(300);
        pressureChart.setPinchZoom(true);
        pressureChart.setPadding(2 , 2 , 2 , 2);
        pressureChart.getLegend().setEnabled(true);
        pressureChart.getLegend().setTextColor(Color.WHITE);

        YAxis yAxisRight = pressureChart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.setTextColor(Color.WHITE);
        yAxisRight.enableAxisLineDashedLine(2f , 4f , 2f);

        YAxis yAxisLeft = pressureChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.WHITE);

        XAxis x = pressureChart.getXAxis();
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setTextColor(Color.parseColor("#FFFFFF"));
        x.setValueFormatter(new XFormatter(dates));

        LineDataSet set;
        if (pressureChart.getData() != null) {
            pressureChart.getData().removeDataSet(pressureChart.getData().getDataSetByIndex(
                    pressureChart.getData().getDataSetCount() - 1));
            pressureChart.getLegend().setTextColor(Color.parseColor("#FFFFFF"));
        }
        set = new LineDataSet(pressureEntries, getString(R.string.g_pressure));
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set.setCubicIntensity(0.2f);
        set.setDrawCircles(false);
        set.setLineWidth(2f);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        set.setColor(Color.CYAN);
        set.setHighlightEnabled(false);
        set.setValueFormatter(mValueFormatter);

        LineData data = new LineData(set);
        pressureChart.setData(data);

        pressureChart.invalidate();
    }

    public void loadSnowChart() {
        snowChart.setDrawGridBackground(false);
        snowChart.setBackgroundColor(Color.BLACK);
        snowChart.setTouchEnabled(true);
        snowChart.setDragEnabled(true);
        snowChart.setMaxHighlightDistance(300);
        snowChart.setPinchZoom(true);
        snowChart.setPadding(2 , 2 , 2 , 2);
        snowChart.getLegend().setEnabled(true);
        snowChart.getLegend().setTextColor(Color.WHITE);

        YAxis yAxisRight = snowChart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.setTextColor(Color.WHITE);
        yAxisRight.enableAxisLineDashedLine(2f , 4f , 2f);

        YAxis yAxisLeft = snowChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.WHITE);

        XAxis x = snowChart.getXAxis();
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setTextColor(Color.parseColor("#FFFFFF"));
        x.setValueFormatter(new XFormatter(dates));

        LineDataSet set;
        if (snowChart.getData() != null) {
            snowChart.getData().removeDataSet(snowChart.getData().getDataSetByIndex(
                    snowChart.getData().getDataSetCount() - 1));
            snowChart.getLegend().setTextColor(Color.parseColor("#FFFFFF"));
        }
        set = new LineDataSet(snowEntries, getString(R.string.g_snow));
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set.setCubicIntensity(0.2f);
        set.setDrawCircles(false);
        set.setLineWidth(2f);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        set.setColor(Color.YELLOW);
        set.setHighlightEnabled(false);
        set.setValueFormatter(mValueFormatter);

        LineData data = new LineData(set);
        snowChart.setData(data);

        snowChart.invalidate();
    }

    public void loadWindChart() {
        windChart.setDrawGridBackground(false);
        windChart.setBackgroundColor(Color.BLACK);
        windChart.setTouchEnabled(true);
        windChart.setDragEnabled(true);
        windChart.setMaxHighlightDistance(300);
        windChart.setPinchZoom(true);
        windChart.setPadding(2 , 2 , 2 , 2);
        windChart.getLegend().setEnabled(true);
        windChart.getLegend().setTextColor(Color.WHITE);

        YAxis yAxisRight = windChart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.setTextColor(Color.WHITE);
        yAxisRight.enableAxisLineDashedLine(2f , 4f , 2f);

        YAxis yAxisLeft = windChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.WHITE);

        XAxis x = windChart.getXAxis();
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setTextColor(Color.parseColor("#FFFFFF"));
        x.setValueFormatter(new XFormatter(dates));

        LineDataSet set;
        if (windChart.getData() != null) {
            windChart.getData().removeDataSet(windChart.getData().getDataSetByIndex(
                    windChart.getData().getDataSetCount() - 1));
            windChart.getLegend().setTextColor(Color.parseColor("#FFFFFF"));
        }
        String wind = getString(R.string.bottom_wind) + ", " + (PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.PREF_TEMPERATURE_UNITS , Constants.METRIC).equals(Constants.METRIC) ? getString(R.string.mps) : getString(R.string.mph));
        set = new LineDataSet(windEntries, wind);
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set.setCubicIntensity(0.2f);
        set.setDrawCircles(false);
        set.setLineWidth(2f);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        set.setColor(Color.RED);
        set.setHighlightEnabled(false);
        set.setValueFormatter(mValueFormatter);

        LineData data = new LineData(set);
        windChart.setData(data);

        windChart.invalidate();
    }

    public void createEntries() {
        ArrayList<WeatherFort.WeatherList> list;
        try {
            list = mDescribable;
            for (int i = 0; i < 10; ++i) {
                long day = list.get(i).getDt();
                long temp = list.get(i).getTemp().getDay();
                long pressure = (long) list.get(i).getPressure();
                long rain;
                try {
                    rain = (long) list.get(i).getRain();
                }
                catch (Exception ex) {
                    rain = 0;
                }
                long snow;
                try {
                    snow = (long) list.get(i).getSnow();
                }
                catch (Exception ex) {
                    snow = 0;
                }
                long wind = (long) list.get(i).getSpeed();
                tempEntries.add(new Entry(i , temp));
                rainEntries.add(new Entry(i , rain));
                pressureEntries.add(new Entry(i , pressure));
                snowEntries.add(new Entry(i , snow));
                windEntries.add(new Entry(i , wind));
                Log.i("Added" , "Entry : " + i + " " + temp);
                dates[i] = getDay(day);
                Log.i("Added" , "Day : " + dates[i]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("Caught" , "JSON Ex");
        }
    }

    public String getDay(long dt) {
        dt *= 1000;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(dt));
        return c.getDisplayName(Calendar.DAY_OF_WEEK , Calendar.SHORT , new Locale(new Prefs(getActivity()).getLanguage()));
    }
}
