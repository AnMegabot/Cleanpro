package com.pakpobox.cleanpro.ui.account.setpsw;

import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:03
 */

public interface SetPSWContract {
    interface ISetPSWPresenter {
        /**
         * 注册
         */
        void register();

        /**
         * 验证登录密码
         * @param isFineVerify 是否为精确校验
         * @return
         */
        boolean verifyPassword(boolean isFineVerify);

        /**
         * 验证支付密码
         * @param isFineVerify 是否为精确校验
         * @return
         */
        boolean verifyPayPassword(boolean isFineVerify);

        /**
         * 验证其他参数
         * @return
         */
        boolean verifyOther();
    }

    interface ISetPSWView extends IView {

        /**
         * 获取登录密码
         * @return
         */
        String getPassword();

        /**
         * 获取确认登录密码
         * @return
         */
        String getEnterPassword();

        /**
         * 获取支付密码
         * @return
         */
        String getPayPassword();

        /**
         * 获取确认支付密码
         * @return
         */
        String getEnterPayPassword();

        /**
         * 获取注册参数实体类
         * @return
         */
        Register getRegisterParam();

        /**
         * 注册成功
         */
        void registerSuccess();
    }
}
