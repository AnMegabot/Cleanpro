package com.pakpobox.cleanpro.ui.logon.register;

import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:11:49
 */

public interface RegisterContract {
    interface IRegisterPresenter {
        void getVerifyCode(String phoneNumber, String countryCode);

        void checkVerifyCode(String phoneNumber, String verifyCode);
    }

    interface IRegisterView extends IView {

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
