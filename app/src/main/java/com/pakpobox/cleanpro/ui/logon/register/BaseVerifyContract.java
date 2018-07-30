package com.pakpobox.cleanpro.ui.logon.register;

import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:11:49
 */

public interface BaseVerifyContract {
    interface IVerifyPresenter {
        void getVerifyCode();

        void checkVerifyCode();

        boolean verifyAccount();

        boolean verifyCode();
    }

    interface IVerifyView extends IView {
        /**
         * 获取用户名
         * @return
         */
        String getUserName();

        /**
         * 获取验证码
         * @return
         */
        String getVerifyCode();

        /**
         * 获取国家代码
         * @return
         */
        String getCountryCode();

        /**
         * 获取验证码成功
         */
        void getSuccess(String result);

        /**
         * 校验验证码成功
         */
        void checkSuccess(String result);
    }

    interface IVerifyModel {
        /**
         * 获取验证码
         * @param phoneNumber 手机号码
         * @param countryCode 国家代码
         * @param callback 回调
         */
        void getVerifyCode(String phoneNumber, String countryCode, NetCallback<String> callback);

        /**
         * 校验验证码
         * @param phoneNumber 手机号码
         * @param verifyCode 验证码
         * @param callback 回调
         */
        void checkVerifyCode(String phoneNumber, String verifyCode, NetCallback<String> callback);
    }
}
