package com.a5corp.weather.utils;


import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.Calendar;
import java.util.Date;

public class XFormatter implements IAxisValueFormatter {

    private String[] dates;

    public XFormatter(String[] dates) {
        this.dates = dates;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Log.i("value" , Float.toString(value));
        return getDay((long) value);
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