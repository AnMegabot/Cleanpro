package com.pakpobox.cleanpro.model.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pakpobox.cleanpro.bean.CreateOrderRequest;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.ResponseDataBean;
import com.pakpobox.cleanpro.model.GlobalData;
import com.pakpobox.cleanpro.model.net.callback.INetCallback;
import com.pakpobox.logger.Logger;

import java.lang.reflect.Type;


/**
 * 网络数据模型
 * User:Sean.Wei
 * Date:2018/3/14
 * Time:14:41
 */

public class NetDataModel {
    private static final String TAG = "NetDataModel";
    private static NetDataModel instance = null;
    private HttpManager httpManager = null;

    private NetDataModel(){
        httpManager = new HttpManager();
    }

    /**
     * 获取单例
     * @return 唯一对象
     */
    public static NetDataModel getInstance(){
        if (instance == null) {
            synchronized (NetDataModel.class) {
                if (instance == null) {
                    instance = new NetDataModel();
                }
            }
        }
        return instance;
    }

    /**
     * 创建订单
     * @param body 参数
     * @param callback
     */
    public void createOrder(CreateOrderRequest body, INetCallback callback) {
        String url = getApiUrl(GlobalData.API_CTEATE_ORDER);
        String requestStr = new Gson().toJson(body);
//        httpManager.asyncPostStringByHttp(url, null, requestStr, new MyHttpResponseCallback(callback, new TypeToken<ResponseDataBean<CreateOrderRequest>>() {}.getType()));
//        httpManager.asyncPostStringByHttp(url, null, requestStr, callback);
        httpManager.asyncGetDataByHttp(url, null, callback);
    }

    //获取接口url
    public String getApiUrl(String apiAction, Object... params) {
        String url;
        url = GlobalData.API_HOST + GlobalData.API_PATH + apiAction;

        return params!=null && params.length!=0 ? String.format(url, params) : url;
    }

    //获取http头
//    private HashMap<String, String> getHttpHeader(){
//
//        HashMap<String, String> headerMap = new HashMap<>();
//        if (null!= AppSetting.getInstance().getCustomer())
//            headerMap.put("userToken", AppSetting.getInstance().getCustomer().getToken());
//
//        return headerMap;
//    }

}
