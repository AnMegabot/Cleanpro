package com.pakpobox.cleanpro.model.net;

/**
 * User:Sean.Wei
 * Date:2018/7/20
 * Time:16:08
 */

public interface NetConfig {
    int REQUEST_SUCCESS = 0; //请求成功
    int REQUEST_ERROR = -1;  //请求失败

    int CONNECT_ERROR = 1001; //连接错误,网络异常
    int CONNECT_TIMEOUT = 1002; //连接超时
    int PARSE_ERROR = 1003; //解析错误
    int UNKNOWN_ERROR = 1004; //未知错误
    int REQUEST_TIMEOUT = 1005; //请求超时
}
