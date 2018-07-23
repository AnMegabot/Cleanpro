package com.pakpobox.cleanpro.ui.mvp.model;

import com.pakpobox.cleanpro.model.net.HttpManager;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:10
 */

public interface IModel {
    /**
     * 使用HttpManager请求数据
     * @return
     */
    HttpManager getHttpRequest();
}
