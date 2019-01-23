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
    public static final String GET_VERIFYCODE = "member/sendVerifyCode";

    /**
     * 校验验证码-注册
     */
    public static final String CHECK_VERIFYCODE = "member/checkVerifyCode";

    /**
     * 校验邀请码
     */
    public static final String GET_INVITECODE = "member/getInviteCode?inviteCode=%s";

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
     * 修改个人信息
     */
    public static final String UPDATE_PROFILE = "/member/updateProfile";

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

}
