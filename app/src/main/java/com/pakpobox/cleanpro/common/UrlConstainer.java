package com.pakpobox.cleanpro.common;

/**
 * api地址
 */
public class UrlConstainer {
//    public static final String BASE_URL = "https://sumscn.storhub.com";//服务器正式
//    public static final String BASE_URL = "https://storhubapish.pakpobox.com";//服务器UAT
    public static final String BASE_URL = "http://192.168.0.240:8098";//服务器测试
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
    public static final String GET_VERIFYCODE = "member/sendVerifyCode";

    /**
     * 校验验证码-注册
     */
    public static final String CHECK_VERIFYCODE = "member/checkVerifyCode";

    /**
     * 获取验证码-找回密码
     */
    public static final String GET_VERIFYCODE_REPSW = "member/retrievePassword";

    /**
     * 校验验证码-找回密码
     */
    public static final String CHECK_VERIFYCODE_REPSW = "member/retrievePasswordVerify";

    /**
     * 重置登录密码
     */
    public static final String CHANGE_PASSWORD = "member/resetPassword";

    /**
     * 重置支付密码
     */
    public static final String CHANGE_PAY_PASSWORD = "member/resetPayPassword";

    /**
     * 校验支付密码
     */
    public static final String CHECK_PAY_PSW = "member/checkPayPassword";

    /**
     * 修改支付密码
     */
    public static final String RESET_PAY_PSW = "member/updatePayPassword";

    /**
     * 获取钱包
     */
    public static final String WALLET = "member/wallet";

    /**
     * 获取钱包流水
     */
    public static final String WALLET_QUERY = "member/walletLog/query?page=%d&maxCount=%d";

    /**
     * 创建订单
     */
    public static final String CTEATE_ORDER = "cleanpro/order/create";

    /**
     * 订单列表
     */
    public static final String ORDER_LIST = "cleanpro/order/query?page=%d&maxCount=%d";

    /**
     * 价格列表
     */
    public static final String PRICE = "goods/member/getWashMachePrice";

}
