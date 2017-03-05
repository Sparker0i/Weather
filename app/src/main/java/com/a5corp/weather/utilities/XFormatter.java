package com.a5corp.weather.utilities;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class XFormatter implements IAxisValueFormatter {
    private String[] mValues;

    public XFormatter (String[] dates) {
        mValues = dates;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mValues[(int) value];
    }
}
