package com.pakpobox.cleanpro.common;

/**
 * App 常量类
 * Created by 康栋普 on 2018/2/1.
 */

public class Const {
    //用户相关
    public static class USERINFO_KEY {
        public static final String USER_INFO = "mUserInfo";  //用户信息
        public static final String IS_LOGIN = "mIsLogin";    //登录状态
        public static final String LAST_LOGIN_ACCOUNT = "mLastLoginAccount";    //上次登录账号
        public static final String AES = "mAES";//用户信息密钥
    }

    //洗衣类型
    public static class CLEAN_TYPE {
        public static final int LAUNDRY = 0;
        public static final int DRYER = 1;
    }

}
