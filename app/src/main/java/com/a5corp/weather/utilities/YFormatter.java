package com.a5corp.weather.utilities;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class YFormatter implements IAxisValueFormatter{

    private DecimalFormat decimalFormat;

    public YFormatter() {
        decimalFormat = new DecimalFormat("#.##");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return decimalFormat.format(value);
    }
}
