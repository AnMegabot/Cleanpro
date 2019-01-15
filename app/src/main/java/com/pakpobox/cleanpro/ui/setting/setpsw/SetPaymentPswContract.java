package com.pakpobox.cleanpro.ui.setting.setpsw;

import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.bean.UserBean;
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
}
