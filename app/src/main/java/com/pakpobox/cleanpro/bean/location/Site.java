package com.pakpobox.cleanpro.bean.location;

import java.util.List;

/**
 * 门店
 * User:Sean.Wei
 * Date:2018/7/31
 * Time:11:38
 */

public class Site {
    private String id;
    private String orderNo;
    private String token;
    private String name;
    private String enName;
    private Operator operator;
    private String currencyUnit;
    private String zoneId;
    private List<String> businessType;
    private String status;
    private int freeLocker;
    private double latitude;
    private double longitude;
    private int showApp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public List<String> getBusinessType() {
        return businessType;
    }

    public void setBusinessType(List<String> businessType) {
        this.businessType = businessType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFreeLocker() {
        return freeLocker;
    }

    public void setFreeLocker(int freeLocker) {
        this.freeLocker = freeLocker;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getShowApp() {
        return showApp;
    }

    public void setShowApp(int showApp) {
        this.showApp = showApp;
    }
}
