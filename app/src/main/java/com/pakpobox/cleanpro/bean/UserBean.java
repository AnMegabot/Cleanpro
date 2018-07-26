package com.pakpobox.cleanpro.bean;

/**
 * User:Sean.Wei
 * Date:2018/7/24
 * Time:10:03
 */

public class UserBean {
    private String ID;
    private String phoneNumber;
    private String loginName;
    private String randomPassword;
    private String randomPasswordExpiryTime;
    private String token;
    private String lastLoginTime;
    private String createTime;
    private String cert_type;
    private String myInviteCode;
    private String infoPercent;
    private String credit;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getRandomPasswordExpiryTime() {
        return randomPasswordExpiryTime;
    }

    public void setRandomPasswordExpiryTime(String randomPasswordExpiryTime) {
        this.randomPasswordExpiryTime = randomPasswordExpiryTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCert_type() {
        return cert_type;
    }

    public void setCert_type(String cert_type) {
        this.cert_type = cert_type;
    }

    public String getMyInviteCode() {
        return myInviteCode;
    }

    public void setMyInviteCode(String myInviteCode) {
        this.myInviteCode = myInviteCode;
    }

    public String getInfoPercent() {
        return infoPercent;
    }

    public void setInfoPercent(String infoPercent) {
        this.infoPercent = infoPercent;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
