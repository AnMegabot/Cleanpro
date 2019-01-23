package com.pakpobox.cleanpro.common;

/**
 * App 常量类
 * Created by 康栋普 on 2018/2/1.
 */

public class Const {
    public static final String QRCODE_AES_KEY = "s8fPakpoE1j676v6";
    //用户相关
    public static class USERINFO_KEY {
        public static final String USER_INFO = "mUserInfo";  //用户信息
        public static final String IS_LOGIN = "mIsLogin";    //登录状态
        public static final String LAST_LOGIN_ACCOUNT = "mLastLoginAccount";    //上次登录账号
    }

    //洗衣类型
    public enum CLEAN_TYPE {
        LAUNDRY,
        DRYER
    }

    public enum GENDER{
        MALE,
        FEMALE
    }

}
