package com.pakpobox.cleanpro.bean;

/**
 * 钱包实体类
 * User:Sean.Wei
 * Date:2018/7/26
 * Time:16:14
 */

public class Wallet {
    private String id;
    private double balance;
    private int credit;
    private String currencyUnit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

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
