package com.pakpobox.cleanpro.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 注册参数实体类
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:18:06
 */

public class Register implements Parcelable {
    private String phoneNumber; //手机号码
    private String loginName; //与手机号码相同
    private String randomPassword; //验证码
    private String password; //登录密码
    private String payPassword; //支付密码
    private String countryCode; //国家代码 例如：中国:86
    private String registerType = "ANDROID"; //注册终端类型
    private String firstName; //first name
    private String lastName; //last name
    private long birthday; //生日 8位纯数字，格式:yyyyMMdd 例如：19911012
    private String gender; //MALE:男，FEMALE:女
    private String postCode; //Post Code inviteCode
    private String inviteCode; //邀请码

    public Register() {

    }

    protected Register(Parcel in) {
        phoneNumber = in.readString();
        loginName = in.readString();
        randomPassword = in.readString();
        password = in.readString();
        payPassword = in.readString();
        countryCode = in.readString();
        registerType = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        birthday = in.readLong();
        gender = in.readString();
        postCode = in.readString();
        inviteCode = in.readString();
    }

    public static final Creator<Register> CREATOR = new Creator<Register>() {
        @Override
        public Register createFromParcel(Parcel in) {
            return new Register(in);
        }

        @Override
        public Register[] newArray(int size) {
            return new Register[size];
        }
    };

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRandomPassword() {
        return randomPassword;
    }

    public void setRandomPassword(String randomPassword) {
        this.randomPassword = randomPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(phoneNumber);
        parcel.writeString(loginName);
        parcel.writeString(randomPassword);
        parcel.writeString(password);
        parcel.writeString(payPassword);
        parcel.writeString(countryCode);
        parcel.writeString(registerType);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeLong(birthday);
        parcel.writeString(gender);
        parcel.writeString(postCode);
        parcel.writeString(inviteCode);
    }
}
