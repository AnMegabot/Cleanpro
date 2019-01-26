package com.pakpobox.cleanpro.bean;

/**
 * User:Sean.Wei
 * Date:2019/1/26
 * Time:14:32
 */

public class PayResult {
    private boolean continue_pay;
    private boolean order_paid;
    private int statusCode;
    private Order order;

    public boolean isContinue_pay() {
        return continue_pay;
    }

    public void setContinue_pay(boolean continue_pay) {
        this.continue_pay = continue_pay;
    }

    public boolean isOrder_paid() {
        return order_paid;
    }

    public void setOrder_paid(boolean order_paid) {
        this.order_paid = order_paid;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
