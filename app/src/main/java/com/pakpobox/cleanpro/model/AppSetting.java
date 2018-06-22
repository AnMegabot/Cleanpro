package com.pakpobox.cleanpro.model;

import android.content.Context;
import android.text.TextUtils;

import com.pakpobox.cleanpro.utils.AESUtil;
import com.pakpobox.cleanpro.utils.SharedPreferenceUtil;

/**
 * App设置数据中心
 * User:Sean.Wei
 * Date:2018/3/9
 * Time:10:ic_launcher
 */

public class AppSetting {
    private static final String TAG = "AppSetting";
    private static AppSetting instance = null;

    private static final String PREFER_FILE_NAME = "Creanpro_config";
    private SharedPreferenceUtil mAppPreference = null;

    private Context mContext = null;

    private GlobalData.Location mLocation = null;

    private boolean hasLogin = false;

    /** 秘钥 */
    public static final String RANDOM_STR = "CN#.r$#uJ#~@";

    private AppSetting(){
    }

    //单例
    public static AppSetting getInstance(){
        if (instance == null) {
            synchronized (AppSetting.class) {
                if (instance == null) {
                    instance = new AppSetting();
                }
            }
        }
        return instance;
    }

    //初始化
    public void init(Context context) {
        mContext = context;
        if(null == mAppPreference)
            mAppPreference = new SharedPreferenceUtil(mContext, PREFER_FILE_NAME);
    }

    //注销登录
    public void logout() {
    }

    /*==================== 全局保存 ======================*/

    public boolean isHasLogin() {
        return hasLogin;
    }

    public void setHasLogin(boolean hasLogin) {
        this.hasLogin = hasLogin;
    }

    /*==================== 本地存储 ======================*/

    //是否第一次登录
    public boolean isFirstStart() {
        if (null != mAppPreference)
            return mAppPreference.getBoolean("key_is_F", true);
        return false;
    }
    public void setFirstStart(boolean isFirst) {
        if (null != mAppPreference)
            mAppPreference.commitBoolean("key_is_F", isFirst);
    }

    //当前地区
    public void setLocation(GlobalData.Location location) {
        mLocation = location;
        commitSpString("key_l", location.name());
    }
    public GlobalData.Location getLocation() {
        if (null != mLocation)
            return mLocation;
        String locationName = getSpString("key_l", GlobalData.Location.SINGAPORE.name());
        mLocation = GlobalData.Location.valueOf(locationName);
        return mLocation;
    }

    //上一次登录成功的手机号码
    public void saveLastPhoneNumb(String phoneNumb) {
        String key = "key_p_n" + getLocation().name();
        commitSpString(key, phoneNumb);
    }
    public String getLastPhoneNumb() {
        String key = "key_p_n" + getLocation().name();
        return getSpString(key, null);
    }


    /*==================== 私有方法 ======================*/

    //提交String信息
    private void commitSpString(String key, String value) {
        if (!TextUtils.isEmpty(value)) {
//            AES aes = new AES();
//            value = aes.parseByte2HexStr(aes.encrypt(value, RANDOM_STR));
            value = AESUtil.encrypt(RANDOM_STR, value);
        }
        if(null != mAppPreference)
            mAppPreference.commitString(key, value);
    }
    //获取String信息
    private String getSpString(String key, String defValue) {
        if(null == mAppPreference)
            return defValue;
        String result = mAppPreference.getString(key, defValue);
        if (TextUtils.isEmpty(result) || result.equals(defValue))
            return result;
//        AES aes = new AES();
        result = AESUtil.decrypt(RANDOM_STR, result);
        return result;
    }

}
