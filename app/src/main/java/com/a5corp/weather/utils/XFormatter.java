package com.a5corp.weather.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class XFormatter implements IAxisValueFormatter {

    private String[] dates;

    public XFormatter(String[] dates) {
        this.dates = dates;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return dates[(int) value];
    }
}