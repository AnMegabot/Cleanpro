package com.pakpobox.cleanpro.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:05
 */

public class Order implements Parcelable {
    private String id;
    private String machine_no;
    private String order_no;
    private double total_amount;
    private String client_type;
    private String order_type;
    private String goods_info;
    private long create_time;
    private String pay_status;
    private String location;
    private String client_version;

    protected Order(Parcel in) {
        id = in.readString();
        machine_no = in.readString();
        order_no = in.readString();
        total_amount = in.readDouble();
        client_type = in.readString();
        order_type = in.readString();
        goods_info = in.readString();
        create_time = in.readLong();
        pay_status = in.readString();
        location = in.readString();
        client_version = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMachine_no() {
        return machine_no;
    }

    public void setMachine_no(String machine_no) {
        this.machine_no = machine_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(String goods_info) {
        this.goods_info = goods_info;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClient_version() {
        return client_version;
    }

    public void setClient_version(String client_version) {
        this.client_version = client_version;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(machine_no);
        parcel.writeString(order_no);
        parcel.writeDouble(total_amount);
        parcel.writeString(client_type);
        parcel.writeString(order_type);
        parcel.writeString(goods_info);
        parcel.writeLong(create_time);
        parcel.writeString(pay_status);
        parcel.writeString(location);
        parcel.writeString(client_version);
    }
}
