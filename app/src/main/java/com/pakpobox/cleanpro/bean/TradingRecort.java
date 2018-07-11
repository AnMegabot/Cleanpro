package com.pakpobox.cleanpro.bean;

/**
 * 交易记录
 * User:Sean.Wei
 * Date:2018/6/27
 * Time:12:16
 */

public class TradingRecort {
    private String ID;
    private String Type;
    private String Time;
    private String TotalAmount;

    public TradingRecort(String ID, String type, String time, String totalAmount) {
        this.ID = ID;
        Type = type;
        Time = time;
        TotalAmount = totalAmount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}
