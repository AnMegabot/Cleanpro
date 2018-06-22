package com.pakpobox.cleanpro.ui.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragmentActivity;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

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
