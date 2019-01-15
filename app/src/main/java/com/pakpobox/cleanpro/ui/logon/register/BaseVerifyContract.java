package com.pakpobox.cleanpro.ui.logon.register;

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
}
