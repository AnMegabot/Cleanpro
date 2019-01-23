package com.pakpobox.cleanpro.ui.account;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragmentActivity;
import com.pakpobox.cleanpro.ui.account.login.LoginFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

public class LoginActivity extends BaseFragmentActivity {

    @Override
    protected void initViews() {
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this);

        if (findFragment(LoginFragment.class) == null) {
            loadRootFragment(R.id.login_container, LoginFragment.newInstance());  // 加载根Fragment
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
}
