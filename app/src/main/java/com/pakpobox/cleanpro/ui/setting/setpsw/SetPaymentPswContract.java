package com.pakpobox.cleanpro.ui.setting.setpsw;

import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:11:49
 */

public interface SetPaymentPswContract {
    interface ISetPaymentPswPresenter {
        /**
         * 校验支付密码
         */
        void checkPayPsw();

        /**
         * 修改支付密码
         */
        void resetPayPsw();

        /**
         * 验证密码输入是否正确
         * @param step 步骤
         * @return
         */
        boolean verifyPayPassword(int step);

    }

    interface ISetPaymentPswView extends IView {
        /**
         * 获取旧密码
         * @return
         */
        String getOldPassword();

        /**
         * 获取新密码1
         * @return
         */
        String getNewPassword1();

        /**
         * 获取新密码2
         * @return
         */
        String getNewPassword2();

        /**
         * 获取Token
         * @return
         */
        String getToken();

        /**
         * 校验支付密码成功
         */
        void checkPayPswSuccess();

        /**
         * 修改支付密码成功
         */
        void resetPayPswSuccess(String result);
    }

    interface ISetPaymentPswModel {
        /**
         * 校验支付密码
         * @param payPassword 支付密码
         * @param callback 回调
         */
        void checkPayPsw(String payPassword, NetCallback<Result> callback);

        /**
         * 校验验证码
         * @param oldPayPassword 旧支付密码
         * @param newPayPassword 新支付密码
         * @param callback 回调
         */
        void resetPayPsw(String oldPayPassword, String newPayPassword, NetCallback<UserBean> callback);

        /**
         * 重置支付密码
         * @param token token
         * @param newPaymentPassword 新支付密码
         * @param callback 回调
         */
        void changePayPSW(String token, String newPaymentPassword, NetCallback<UserBean> callback);
    }
}
