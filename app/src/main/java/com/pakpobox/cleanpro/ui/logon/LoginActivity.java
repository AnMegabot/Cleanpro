package com.pakpobox.cleanpro.ui.logon;

import android.os.Bundle;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragmentActivity;
import com.pakpobox.cleanpro.ui.logon.login.LoginFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

public class LoginActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this);

        if (findFragment(LoginFragment.class) == null) {
            loadRootFragment(R.id.login_container, LoginFragment.newInstance());  // 加载根Fragment
        }
    }
}
