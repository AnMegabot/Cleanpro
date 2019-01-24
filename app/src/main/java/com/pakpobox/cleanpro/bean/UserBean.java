package com.pakpobox.cleanpro.bean;

/**
 * User:Sean.Wei
 * Date:2018/7/24
 * Time:10:03
 */

public class UserBean {
    private String id;
    private String phoneNumber;
    private String loginName;
    private String firstName;
    private String lastName;
    private String postCode;
    private String payPassword;
    private String token;
    private String lastLoginTime;
    private String createTime;
    private String cert_type;
    private String inviteCode;
    private String myInviteCode;
    private String gender;
    private long birthday;
    private float infoPercent;
    private int credit;
    private int couponCount;
    private String randomPassword;
    private String randomPasswordExpiryTime;
    private String headImageUrl;
    private Wallet wallet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
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

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getMyInviteCode() {
        return myInviteCode;
    }

    public void setMyInviteCode(String myInviteCode) {
        this.myInviteCode = myInviteCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public float getInfoPercent() {
        return infoPercent;
    }

    public void setInfoPercent(float infoPercent) {
        this.infoPercent = infoPercent;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
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

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
