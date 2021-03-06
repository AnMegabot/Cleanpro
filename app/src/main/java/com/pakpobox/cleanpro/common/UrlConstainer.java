package com.pakpobox.cleanpro.common;

/**
 * api地址
 */
public class UrlConstainer {
//    public static final String BASE_URL = "https://sumscn.storhub.com";//服务器正式
    public static final String BASE_URL = "http://47.90.16.40:18987";//服务器UAT
//    public static final String BASE_URL = "http://192.168.0.240:8098";//服务器测试
    //api路径
    public static final String API_PATH = "/ebox/api/v1/";

    /**
     * 登录
     */
    public static final String LOGIN = "member/login";

    /**
     * 注册
     */
    public static final String REGISTER = "member/phoneRegister";

    /**
     * 获取验证码-注册
     */
    public static final String GET_REGISTER_VERIFYCODE = "member/sendVerifyCode";

    /**
     * 校验验证码-注册
     */
    public static final String CHECK_REGISTER_VERIFYCODE = "member/checkVerifyCode";

    /**
     * 获取验证码-当前账号
     */
    public static final String GET_CURRENT_VERIFYCODE = "member/sendAccountVerifyCode";

    /**
     * 校验验证码-当前账号
     */
    public static final String CHECK_CURRENT_VERIFYCODE = "member/checkAccountVerifyCode";

    /**
     * 获取验证码-找回密码
     */
    public static final String GET_REPSW_VERIFYCODE = "member/retrievePassword";

    /**
     * 校验验证码-找回密码
     */
    public static final String CHECK_REPSW_VERIFYCODE = "member/retrievePasswordVerify";

    /**
     * 校验邀请码
     */
    public static final String GET_INVITECODE = "member/getInviteCode?inviteCode=%s";

    /**
     * 重置登录密码
     */
    public static final String CHANGE_PASSWORD = "member/resetPassword";

    /**
     * 设置支付密码
     */
    public static final String SET_PAYMENT_PASSWORD = "member/setPayPassword";

    /**
     * 重置支付密码
     */
    public static final String RESET_PAY_PSW = "member/resetPayPassword";

    /**
     * 更换支付密码
     */
    public static final String CHANGE_PAY_PSW = "member/updatePayPassword";

    /**
     * 校验支付密码
     */
    public static final String CHECK_PAY_PSW = "member/checkPayPassword";

    /**
     * 获取钱包
     */
    public static final String WALLET = "member/wallet";

    /**
     * 获取钱包流水
     */
    public static final String WALLET_QUERY = "member/walletLog/query?page=%d&maxCount=%d";

    /**
     * 获取个人信息
     */
    public static final String GET_USER_INFO = "member/userInfo";

    /**
     * 修改个人信息
     */
    public static final String UPDATE_PROFILE = "member/updateProfile";

    /**
     * 修改用户头像
     */
    public static final String UPLOAD_HEAD_IMAGE = "member/uploadHeadImage";

    /**
     * 获取用户头像
     */
    public static final String GET_HEAD_IMAGE = "member/downloadHeadImage/%s";

//    /**
//     * 创建订单
//     */
//    public static final String CTEATE_ORDER = "cleanpro/order/create";

    /**
     * 创建订单
     */
    public static final String CTEATE_ORDER = "cleanpro/order/checkout";

    /**
     * 订单列表
     */
    public static final String ORDER_LIST = "cleanpro/order/query?page=%d&maxCount=%d";

    /**
     * 价格列表
     */
    public static final String PRICE = "goods/member/getWashMachePrice";

    /**
     * 获取指定机器价格列表
     */
    public static final String MACHINE_PRICE = "goods/member/getMachePrice?macheNo=%s";

    /**
     * 门店列表
     */
    public static final String LOCATION = "cleanpro/location?page=%d&maxCount=%d";

    /**
     * 门店列表
     */
    public static final String CREATE_FEEDBACK = "help/createFeedback";

}
