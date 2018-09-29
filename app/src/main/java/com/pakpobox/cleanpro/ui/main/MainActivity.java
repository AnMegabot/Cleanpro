package com.pakpobox.cleanpro.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragmentActivity;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity {

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        StatusBarUtil.transparencyBar(this);
//        StatusBarUtil.StatusBarLightMode(this);

        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.main_fl_container, MainFragment.newInstance(), true, true);
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                Toast.makeText(this, R.string.app_press_again_exit, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
    }
}
