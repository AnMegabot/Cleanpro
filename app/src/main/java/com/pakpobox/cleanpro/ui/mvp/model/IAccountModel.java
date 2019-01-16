package com.pakpobox.cleanpro.ui.mvp.model;

import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.net.callback.INetCallback;

/**
 * User:Sean.Wei
 * Date:2019/1/10
 * Time:17:35
 */

public interface IAccountModel {
    /**
     * 注册
     * @param register 注册参数
     * @param callback 回调
     */
    void register(Register register, INetCallback callback);

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @param callback 回调
     */
    void login(String username, String password, INetCallback callback);

    /**
     * 重置密码
     * @param token token
     * @param newPassword 新登录密码
     * @param callback 回调
     */
    void changePSW(String token, String newPassword, INetCallback callback);

    /**
     * 获取注册验证码
     * @param phoneNumber 手机号码
     * @param countryCode 国家代码
     * @param callback 回调
     */
    void getRegisterVerifyCode(String phoneNumber, String countryCode, INetCallback callback);

    /**
     * 获取忘记密码验证码
     * @param phoneNumber 手机号码
     * @param countryCode 国家代码
     * @param callback 回调
     */
    void getForgetPSWVerifyCode(String phoneNumber, String countryCode, INetCallback callback);

    /**
     * 校验注册验证码
     * @param phoneNumber 手机号码
     * @param verifyCode 验证码
     * @param callback 回调
     */
    void checkRegisterVerifyCode(String phoneNumber, String verifyCode, INetCallback callback);

    /**
     * 校验忘记密码验证码
     * @param phoneNumber 手机号码
     * @param verifyCode 验证码
     * @param callback 回调
     */
    void checkForgetPSWVerifyCode(String phoneNumber, String verifyCode, INetCallback callback);

    /**
     * 校验支付密码
     * @param payPassword 支付密码
     * @param callback 回调
     */
    void checkPayPsw(String payPassword, INetCallback callback);

    /**
     * 重置支付密码
     * @param oldPayPassword 旧支付密码
     * @param newPayPassword 新支付密码
     * @param callback 回调
     */
    void resetPayPsw(String oldPayPassword, String newPayPassword, INetCallback callback);

    /**
     * 修改支付密码
     * @param token token
     * @param newPaymentPassword 新支付密码
     * @param callback 回调
     */
    void changePayPSW(String token, String newPaymentPassword, INetCallback callback);

    /**
     * 获取钱包
     * @param callback 回调
     */
    void getWallet(INetCallback callback);

    /**
     * 获取钱包流水
     * @param page 页数
     * @param maxCount 每页条数
     * @param callback 回调
     */
    void getRechargeDetailList(int page, int maxCount, INetCallback callback);
}