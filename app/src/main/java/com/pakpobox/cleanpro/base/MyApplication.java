package com.pakpobox.cleanpro.base;

import android.app.Application;
import android.content.Context;

import com.pakpobox.cleanpro.model.AppSetting;
import com.pakpobox.cleanpro.utils.language.LanguageUtil;
import com.pakpobox.cleanpro.utils.language.MyContextWrapper;
import com.pakpobox.logger.Logger;
import com.pakpobox.logger.adapter.AndroidLogAdapter;
import com.pakpobox.logger.formatstrategy.TxtFormatStrategy;
import com.pakpobox.logger.logstrategy.LogcatLogStrategy;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Locale;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * 应用入口类
 * User:Sean.Wei
 * Date:2018/2/8
 * Time:11:18
 */

public class MyApplication extends Application{
    private static Context mContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //腾讯Bugly
        CrashReport.initCrashReport(getApplicationContext(), "5e3b64cd68", true);

        //初始化Logger
        Logger.addLogAdapter(new AndroidLogAdapter(TxtFormatStrategy.newBuilder().tag("Creanpro").logStrategy(new LogcatLogStrategy()).build()));
        //初始化AppSetting
        AppSetting.getInstance().init(getApplicationContext());

        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
                /**
                 * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}
                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                 */
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // .. create or get your new Locale object here.
        Locale newLocale = LanguageUtil.getAppLocale(newBase);
        Context context = MyContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }

    public static Context getContext() {
        return mContext;
    }

}
