package com.pakpobox.cleanpro.bean;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:05
 */

public class Order {
    private String ID;
    private String Type;
    private String PaymentStatus;
    private String OrderNo;
    private String TotalAmount;

    public Order(String ID, String type, String paymentStatus, String orderNo, String totalAmount) {
        this.ID = ID;
        Type = type;
        PaymentStatus = paymentStatus;
        OrderNo = orderNo;
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

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}
