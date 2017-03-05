package com.a5corp.weather.utilities;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class CustomFormatter implements IValueFormatter {

    private DecimalFormat decimalFormat;

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return decimalFormat.format(value);
    }

    public CustomFormatter() {
        decimalFormat = new DecimalFormat("#.##");
    }
}
