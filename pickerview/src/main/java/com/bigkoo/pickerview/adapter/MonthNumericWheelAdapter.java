package com.bigkoo.pickerview.adapter;

import com.contrarywind.adapter.WheelAdapter;

/**
 * User:Sean.Wei
 * Date:2019/1/16
 * Time:17:19
 */

public class MonthNumericWheelAdapter implements WheelAdapter {

    private static final String[] MONTH_NUM = {"Jan.", "Feb.", "Mar.", "Apr.", "May.", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};
    private int minValue;
    private int maxValue;

    /**
     * Constructor
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public MonthNumericWheelAdapter(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            return value > 0 && value <= MONTH_NUM.length ? MONTH_NUM[value-1] : String.valueOf(value);
        }
//
        return MONTH_NUM[0];
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }

    @Override
    public int indexOf(Object o){
        try {
            return (int)o - minValue;
        } catch (Exception e) {
            return -1;
        }

    }
}
