package com.pakpobox.cleanpro.ui.logon.login;

import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/7/19
 * Time:15:59
 */

public class LoginPresenter extends BasePresenter<LoginContract.ILoginView> implements LoginContract.ILoginPresenter{
    private String userName, password;
    private LoginContract.ILoginModel mLoginModel;

    public LoginPresenter() {
    }

    @Override
    public void login() {

    }
}
