package com.yalantis.beamazingtoday.sample;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class MyFormatter implements IAxisValueFormatter
{
    private List<String> mValues;
    public MyFormatter(ArrayList<String> values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if(((int)value >=0 && (int)value < mValues.size()))
            return mValues.get((int) value);
        else
                return "";
    }

}