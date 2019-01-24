package com.pakpobox.cleanpro.ui.account.paypsw;

import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:11:49
 */

public interface PaymentPswContract {
    interface IPaymentPswPresenter {
        /**
         * 校验支付密码
         */
        void checkPayPsw(String oldPsw);

        /**
         * 重置支付密码
         */
        void resetPayPsw(String token, String newPsw);

        /**
         * 更换支付密码
         */
        void changePayPsw(String oldPsw, String newPsw);

    }

    interface IPaymentPswView extends IView {

        /**
         * 校验支付密码成功
         */
        void checkPayPswSuccess();

        /**
         * 重置支付密码成功
         */
        void resetPayPswSuccess(String result);

        /**
         * 更换支付密码成功
         */
        void changePayPswSuccess(String result);
    }
}
