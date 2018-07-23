package com.pakpobox.cleanpro.bean;

import java.util.List;

/**
 * 一次性费用对象
 * User:Sean.Wei
 * Date:2018/3/16
 * Time:16:41
 */

public class Setting {
    private double DepositMonth;
    private int NoticePeriodDays;

    public double getDepositMonth() {
        return DepositMonth;
    }

    public void setDepositMonth(double depositMonth) {
        DepositMonth = depositMonth;
    }

    public int getNoticePeriodDays() {
        return NoticePeriodDays;
    }

    public void setNoticePeriodDays(int noticePeriodDays) {
        NoticePeriodDays = noticePeriodDays;
    }

}
