package com.pakpobox.cleanpro.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.pakpobox.cleanpro.utils.SoftInputUtil;
import com.pakpobox.cleanpro.utils.language.LanguageUtil;
import com.pakpobox.cleanpro.utils.language.MyContextWrapper;

import java.util.Locale;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * FragmentActivity基类
 * User:Sean.Wei
 * Date:2018/2/28
 * Time:15:ic_launcher
 */

public class BaseFragmentActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹球或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (null != v && (v instanceof EditText)) {
                View mRootView = v.getRootView();
                mRootView.setClickable(true);
                mRootView.setFocusable(true);
                mRootView.setFocusableInTouchMode(true);
                mRootView.requestFocusFromTouch();
            }

            SoftInputUtil.hideSoftInput(this,v, ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // .. create or get your new Locale object here.
        Locale newLocale = LanguageUtil.getAppLocale(newBase);
        Context context = MyContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }
}
