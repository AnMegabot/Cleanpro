package com.pakpobox.cleanpro.ui.mvp.view;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/6/20
 * Time:18:40
 */

public interface IListDataView<T> extends IView {

    int getPage();

    void setData(List<T> data);

    List<T> getData();

    void showContent(); //显示内容

    void autoLoadMore();//自动加载

    void clearListData();//清空所有数据

    void showNoMore();//没有更多数据

}
