package com.pakpobox.cleanpro.bean;

/**
 * 注册参数实体类
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:18:06
 */

public class Register {
    private String phoneNumber; //手机号码
    private String loginName; //与手机号码相同
    private String randomPassword; //验证码
    private String password; //登录密码
    private String payPassword; //支付密码
    private String countryCode; //国家代码 例如：中国:86

    public Register() {
    }

    public Register(String phoneNumber, String loginName, String randomPassword, String password, String payPassword, String countryCode) {
        this.phoneNumber = phoneNumber;
        this.loginName = loginName;
        this.randomPassword = randomPassword;
        this.password = password;
        this.payPassword = payPassword;
        this.countryCode = countryCode;
    }

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
}
