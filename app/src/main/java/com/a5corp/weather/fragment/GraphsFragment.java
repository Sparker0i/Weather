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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GraphsFragment extends Fragment {

    View rootView;
    Handler handler;
    LineChart chart;
    List<Entry> temperatures;
    LineDataSet dataSet;
    List<Entry> entries = new ArrayList<Entry>(10);
    FetchWeather fw;
    Preferences pf;

    public GraphsFragment() {
        // Required empty public constructor
        handler = new Handler();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fw = new FetchWeather(getContext());
        pf = new Preferences(getContext());
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
        JSONObject[] str = null ;
        JSONObject str1;
        JSONArray list;
        Temperature[] x = new Temperature[10];
        try {
            str = fw.execute(pf.getCity()).get();
            System.out.println(str[1].toString());
        }
        catch(InterruptedException iex) {
            iex.printStackTrace();
        }
        catch (ExecutionException eex) {
            eex.printStackTrace();
        }
            try {
                //str = new JSONObject(bundle.getString("json", null));
                str1 = str[1];
                list = str1.getJSONArray("list");
                for (int i = 0; i < 10; ++i) {
                    //SimpleDateFormat simpleDateformat = new SimpleDateFormat("E" , Locale.US);
                    long day = list.getJSONObject(i).getLong("dt");
                    long temp = list.getJSONObject(i).getJSONObject("temp").getLong("day");
                    x[i] = new Temperature(day, temp);
                    Log.i("added" , i + "to temperature");
                }
                for (Temperature data : x) {

                    // turn your data into Entry objects
                    entries.add(new Entry(data.getDate(), data.getTemp()));
                    Log.i("added" , data + "to temperature");
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
    }

    public void loadChart() {
        dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(Color.parseColor("#FF0000"));
        dataSet.setValueTextColor(Color.parseColor("#FFFFFF"));
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }
}

class Temperature {
    private long date;
    private long temp;

    Temperature(long str , long temp) {
        this.date = str;
        this.temp = temp;
    }

    public long getDate() {
        return date;
    }

    public long getTemp() {
        return temp;
    }
}
