package com.pakpobox.cleanpro.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User:Sean.Wei
 * Date:2018/7/5
 * Time:17:50
 */

public class CreateOrderRequest implements Parcelable {
    private String machine_no;
    private double total_amount;
    private double pay_amount;
    private String client_type = "ANDROID";
    private String order_type;
    private String goods_info;
    private String location;
    private String client_version;
    private int credits = 0;//使用积分
    private String coupon_code;//优惠券编码
    private String payment_platform = "IPAY88";//支付平台。 钱包:WALLET, iPay88:IPAY88

    public CreateOrderRequest() {

    }

    protected CreateOrderRequest(Parcel in) {
        machine_no = in.readString();
        total_amount = in.readDouble();
        pay_amount = in.readDouble();
        client_type = in.readString();
        order_type = in.readString();
        goods_info = in.readString();
        location = in.readString();
        client_version = in.readString();
        credits = in.readInt();
        coupon_code = in.readString();
        payment_platform = in.readString();
    }

    public static final Creator<CreateOrderRequest> CREATOR = new Creator<CreateOrderRequest>() {
        @Override
        public CreateOrderRequest createFromParcel(Parcel in) {
            return new CreateOrderRequest(in);
        }

        @Override
        public CreateOrderRequest[] newArray(int size) {
            return new CreateOrderRequest[size];
        }
    };

    public String getMachine_no() {
        return machine_no;
    }

    public void setMachine_no(String machine_no) {
        this.machine_no = machine_no;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(double pay_amount) {
        this.pay_amount = pay_amount;
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

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getPayment_platform() {
        return payment_platform;
    }

    public void setPayment_platform(String payment_platform) {
        this.payment_platform = payment_platform;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(machine_no);
        parcel.writeDouble(total_amount);
        parcel.writeDouble(pay_amount);
        parcel.writeString(client_type);
        parcel.writeString(order_type);
        parcel.writeString(goods_info);
        parcel.writeString(location);
        parcel.writeString(client_version);
        parcel.writeInt(credits);
        parcel.writeString(coupon_code);
        parcel.writeString(payment_platform);
    }
}
