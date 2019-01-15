package com.pakpobox.cleanpro.utils.language;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Locale;

/**
 * App语言设置工具
 * User:Sean.Wei
 * Date:2018/3/26
 * Time:20:07
 */

public class LanguageUtil {
    public static final String TAG = LanguageUtil.class.getSimpleName();
    private static final String LANGUAGE = "Language";
    private static final String COUNTRY = "country";
    private static final String SCRIPT = "script";

    /**
     * 设置App语言
     * @param context
     * @param locale
     * @param cls
     */
    public static boolean setAppLanguage(Context context, Locale locale, Class<?> cls) {
        if (!locale.equals(getAppLocale(context))) {
            saveAppLocale(context, locale);
            restartApp(context, cls);
            return true;
        }
        return false;
    }

    /**
     * 判断是否等于系统设置
     * @param context
     * @return
     */
    public static boolean checkIsSameLanguage(Context context) {
        Locale appLocale = getAppLocale(context);
        return appLocale.equals(Locale.getDefault());
    }

    //保存语言更改Locale后重启Activity
    private static void restartApp(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        // 杀掉进程
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
    }

    //保存更改
    public static void saveAppLocale(Context context, Locale locale) {
        SharedPreferences preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANGUAGE, locale.getLanguage());
        editor.putString(COUNTRY, locale.getCountry());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editor.putString(SCRIPT, locale.getScript());
        }
        editor.apply();
    }

    //获取App设置的Locale
    public static Locale getAppLocale(Context context) {
        Locale locale = new Locale(getAppLanguage(context), getAppCountry(context));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new Locale.Builder()
                    .setLocale(locale)
                    .setScript(getAppScript(context))
                    .build();
        } else {
            return locale;
        }
    }
    //获取App设置的语言
    private static String getAppLanguage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return preferences.getString(LANGUAGE, Locale.getDefault().getLanguage());
    }
    //获取App设置的国家
    private static String getAppCountry(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return preferences.getString(COUNTRY, Locale.getDefault().getCountry());
    }
    //获取App设置的脚本
    private static String getAppScript(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return preferences.getString(SCRIPT, Locale.getDefault().getScript());
        } else {
            return "";
        }
    }


    /**
     * 注册语言地区广播接收器
     * @param context 上下文
     * @param LocaleChangeReceiver 语言地区广播接收器
     */
    public static void registerLocaleChangeReceiver(Context context, LocaleChangeReceiver LocaleChangeReceiver) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        context.registerReceiver(LocaleChangeReceiver, filter);
    }

    class LocaleChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //改变系统语言会接收到 ACTION_LOCALE_CHANGED
            //改变应用内部语言不会收到 ACTION_LOCALE_CHANGED
            if (!Intent.ACTION_LOCALE_CHANGED.equals(intent.getAction())) {
                return;
            }
        }
    }
}
