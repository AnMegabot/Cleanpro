package com.pakpobox.cleanpro.application;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.common.Const;
import com.pakpobox.cleanpro.net.HttpManager;
import com.pakpobox.cleanpro.utils.AesEncryptionUtils;
import com.pakpobox.cleanpro.utils.PreUtils;

import javax.crypto.spec.SecretKeySpec;

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

    private HttpManager httpManager = null;

    private Context mContext = null;

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
        PreUtils.init(mContext, PREFER_FILE_NAME);
        httpManager = new HttpManager();
    }

    public HttpManager getHttpManager() {
        return httpManager;
    }

    /*==================== 本地存储 ======================*/

    //用户信息
    public static UserBean getUserInfo() {
        UserBean userBean = null;
        String userInfo = getVelueWithDecrypt(Const.USERINFO_KEY.USER_INFO);
        if (TextUtils.isEmpty(userInfo)) return null;
        try {
            userBean = new Gson().fromJson(userInfo, UserBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return userBean;
    }
    public static void saveUserInfo(UserBean userBean){
        String userInfo = new Gson().toJson(userBean);
        saveVelueWithEncrypt(Const.USERINFO_KEY.USER_INFO, userInfo);
    }

    //是否已登录
    public static boolean isLogin() {
        return (boolean) PreUtils.get(Const.USERINFO_KEY.IS_LOGIN, false);
    }
    public static void saveIsLogin(boolean isLogin){
        PreUtils.put(Const.USERINFO_KEY.IS_LOGIN,isLogin);
    }

    //上一次登录成功的手机号码
    public static String getLastPhoneNumb() {
        return (String) PreUtils.get(Const.USERINFO_KEY.LAST_LOGIN_ACCOUNT, "");
    }
    public static void saveLastPhoneNumb(String phoneNumb) {
        PreUtils.put(Const.USERINFO_KEY.LAST_LOGIN_ACCOUNT, phoneNumb);
    }

    /*==================== 私有方法 ======================*/

    //获取解码后的字符串
    private static String getVelueWithDecrypt(String key) {
        SecretKeySpec keySpec = getAesKey();
        return AesEncryptionUtils.decrypt(keySpec, (String) PreUtils.get(key, ""));
    }

    //保存加密后字符串
    private static void saveVelueWithEncrypt(String key, String velue) {
        SecretKeySpec keySpec = getAesKey();
        String aesContent = AesEncryptionUtils.encrypt(keySpec, velue);
        PreUtils.put(key, aesContent);
    }

    //获取AES密钥
    private static SecretKeySpec getAesKey(){
        String keyStr = (String) PreUtils.get(Const.USERINFO_KEY.AES, "");
        if (TextUtils.isEmpty(keyStr)) {
            //生成一个随机密钥
            SecretKeySpec key = AesEncryptionUtils.createKey();
            //保存密钥
            PreUtils.put(Const.USERINFO_KEY.AES, Base64.encodeToString(key.getEncoded(),Base64.DEFAULT));
            return key;
        } else {
            return AesEncryptionUtils.getSecretKey(Base64.decode(keyStr, Base64.DEFAULT));
        }
    }
}
