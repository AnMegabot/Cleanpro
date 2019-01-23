package com.pakpobox.cleanpro.ui.account.login;

import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * 登录页面接口
 * User:Sean.Wei
 * Date:2018/7/13
 * Time:18:09
 */

public interface LoginContract {
    interface ILoginPresenter {
        void login();

        boolean verifyAccount();
    }

    interface ILoginView extends IView{
        /**
         * 获取用户名
         * @return
         */
        String getUserName();

        /**
         * 获取密码
         * @return
         */
        String getPassWord();

        void loginSuccess();
    }
}
