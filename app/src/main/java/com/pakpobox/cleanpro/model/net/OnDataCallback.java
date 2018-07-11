package com.pakpobox.cleanpro.model.net;

import java.util.List;

/**
 * 网络数据请求回调
 * User:Sean.Wei
 * Date:2018/3/14
 * Time:14:30
 */
public interface OnDataCallback<T> {
    int STATUS_API_ERR = -1; //API内部错误
    int STATUS_SUCCESS = 0; //成功
    int STATUS_PARAM_ERR = 1; //参数错误
    int STATUS_CUSTOMER_EXIST = 2; //客户记录已存在
    int STATUS_IDNO_ERR = 5; //身份证号不匹配
    int STATUS_VALIDATE_CODE_INVALID = 11; //验证码无效
    int STATUS_VALIDATE_CODE_PAST = 12; //验证码过期
    int STATUS_WAIT_VALIFY = 202; //账号已经在SRMS注册，需要验证身份证

    void onError(int responseCode, String errMsg);

    void onData(int statusCode, String msg, T data, List<T> datas);
}
