package com.pakpobox.cleanpro.ui.account.register;

import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:11:49
 */

public interface RegisterContract {
    interface IVerifyMobilePresenter {
        void getVerifyCode(String phoneNumber, String countryCode);

        void checkVerifyCode(String phoneNumber, String verifyCode);
    }

    interface IRegisterPresenter {
        void checkInviteCode(String inviteCode);

        void register(Register register);
    }

    interface IVerifyMobileView extends IView {

        /**
         * 获取验证码成功
         */
        void getSuccess(String result);

        /**
         * 校验验证码成功
         */
        void checkSuccess(String result);
    }

    interface IRegisterView extends IView  {
        /**
         * 校验邀请码成功
         */
        void checkInviteCodeSuccess(String result);

        /**
         * 注册成功
         * @param userBean
         */
        void registerSuccess(UserBean userBean);
    }

}
