package com.pakpobox.cleanpro.model;

/**
 * User:Sean.Wei
 * Date:2018/3/9
 * Time:16:59
 */

public class GlobalData {
    //api域名地址
//    public static final String API_HOST_CHINA = "https://sumscn.storhub.com";//服务器正式
//    public static final String API_HOST_CHINA = "https://storhubapish.pakpobox.com";//服务器UAT
    public static final String API_HOST = "http://192.168.0.240:8098";//服务器测试
    //api路径
    public static final String API_PATH = "/ebox/api/v1/";
    //api接口
    public static final String API_CTEATE_ORDER = "cleanpro/order/create"; //创建订单

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
    public enum MachineType {
        LAUNDRY,//洗衣
        DRYER //烘干
    }

}
