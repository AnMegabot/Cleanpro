package com.pakpobox.cleanpro.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.pakpobox.cleanpro.utils.KeyBoardHelper;
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
    private KeyBoardHelper keyBoardHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyBoardHelper = new KeyBoardHelper(this);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //设置点击非EditText区域时隐藏软键盘
        keyBoardHelper.hideKeyboardByTouchOutsize(ev, true);
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
