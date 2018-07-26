package com.pakpobox.cleanpro.ui.logon.login;

import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.net.callback.NetCallback;
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

    interface ILoginModel {
        /**
         * 登录
         * @param username 用户名
         * @param password 密码
         * @param callback 回调
         */
        void login(String username, String password, NetCallback<UserBean> callback);

        /**
         * 保存用户信息
         * @param userBean 用户信息
         */
        void saveUserInfo(UserBean userBean);
    }
}
