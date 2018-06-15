package com.pakpobox.cleanpro.model;

/**
 * User:Sean.Wei
 * Date:2018/3/9
 * Time:16:59
 */

public class GlobalData {
    //api域名地址

//    public static final String API_HOST_SINGAPORE = "https://sumssg.storhub.com";//新加坡服务器正式
    public static final String API_HOST_SINGAPORE = "https://storhubapi.pakpobox.com";//新加坡服务器UAT
//    public static final String API_HOST_SINGAPORE = "http://192.168.0.240:8999";//新加坡服务器测试

//    public static final String API_HOST_CHINA = "https://sumscn.storhub.com";//中国服务器正式
    public static final String API_HOST_CHINA = "https://storhubapish.pakpobox.com";//中国服务器UAT
//    public static final String API_HOST_CHINA = "http://192.168.0.240:8999";//中国服务器测试
    //api路径
    public static final String API_PATH = "/ebox/api/v1/";
    //api接口
    public static final String API_ACTION_GET_VALIDATECODE = "customer/getValiteCode?phone=%s"; //获取验证码
    public static final String API_ACTION_LOGIN = "customer/login"; //登录
    public static final String API_GET_CUSTOMER_INFO = "customer/getCustomerInfo?CustomerID=%s"; //获取用户信息
    public static final String API_UPDATE_CUSTOMER_INFO = "customer/updateCustomerInfo"; //更新用户信息
    public static final String API_VERIFY_ID = "customer/verifyID"; //验证身份证
    public static final String API_GET_SITE_LIST = "house/getSites?City=%s"; //获取门店列表
    public static final String API_GET_SIZE_TYPE_LIST = "house/getSizeTypes?SiteID=%s&StartDate=%s"; //获取仓型列表
    public static final String API_GET_SPACE_LIST = "house/getSpaces?SiteID=%s&SizeTypeID=%s&StartDate=%s"; //获取仓位列表
    public static final String API_GET_DISCOUNT_PLAN = "house/getDiscountPlan?SiteID=%s&SpaceID=%s&StartDate=%s&PrepaymentMonth=%s&Price=%s"; //获取折扣信息
    public static final String API_CREATE_LEASE_OFFER = "lease/createLeaseOffer"; //创建订单
    public static final String API_GET_LEASE_OFFER_LIST = "lease/getLeaseOffer?CustomerID=%s"; //获取订单列表
    public static final String API_GET_LEASE_OFFER_INFO = "lease/getLeaseOffer?CustomerID=%s&LeaseOfferID=%s"; //获取订单详细信息
    public static final String API_CREATE_COLLECTION = "lease/createCollection"; //创建收款信息
    public static final String API_GET_LEASE = "lease/getLease?CustomerID=%s&LeaseOfferID=%s"; //查看租约信息
    public static final String API_GET_FINANCE_HISTORY = "lease/getFinanceHistory?LeaseID=%s"; //查看交易记录
    public static final String API_TERMINATE_LEASE = "lease/terminateLease"; //退仓
    public static final String API_GET_SETTING = "setting/getSettings.ashx"; //获取系统设置
    public static final String API_GET_PAYMENT = "payment/checkout"; //获取支付接口
    public static final String API_GET_WECHAT_PAYMENT = "payment"; //获取微信支付接口
    public static final String API_SEND_LEASE_AGREEMENT_EMAIL = "lease/sendLeaseAgreementEmail"; //发送租约合同邮件
    public static final String API_SEND_INVOICE_EMAIL = "lease/sendInvoiceEmail"; //发送账单邮件接口

    /**
     * 地区枚举类
     */
    public enum Location {
        SINGAPORE,//新加坡
        CHINA //中国
    }

    /**
     * 支付类型
     */
    public enum PaymentType {
        OCBC,//
        WECHAT //微信支付
    }

    /**
     * 证件类型枚举类
     */
    public enum IDType {
        ID,//身份证
        PASSPORT, //护照
        FIN //军官证
    }

    /**
     * 仓型枚举类
     */
    public enum SizeTypeTab {
        Mini("Locker", 0), XXS("XX Small", 104.96), XS("X Small", 164.0), S("Small", 328.0), M("Medium", 656.0), L("Large", 1312.0), XL("X Large", 1968.0), XXL("XX Large", 7062.94);

        private String mTypeName;
        private double cubeFt;//最大立方英尺
        SizeTypeTab(String typeName) {
            this.mTypeName = typeName;
        }

        SizeTypeTab(String typeName, double cubeFt) {
            this.mTypeName = typeName;
            this.cubeFt = cubeFt;
        }

        /**
         * 根据类型的名称，返回类型的枚举实例。
         * @param typeName 类型名称
         */
        public static SizeTypeTab fromTypeName(String typeName) {
            for (SizeTypeTab type : SizeTypeTab.values()) {
                if (type.getTypeName().equals(typeName)) {
                    return type;
                }
            }
            return null;
        }

        public String getTypeName() {
            return this.mTypeName;
        }

        public double getCubeFt() {
            return cubeFt;
        }
    }
}
