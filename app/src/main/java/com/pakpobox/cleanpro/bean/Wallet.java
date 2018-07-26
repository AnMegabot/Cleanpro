package com.pakpobox.cleanpro.bean;

/**
 * 钱包实体类
 * User:Sean.Wei
 * Date:2018/7/26
 * Time:16:14
 */

public class Wallet {
    private double balance;
    private String currencyUnit;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }
}
