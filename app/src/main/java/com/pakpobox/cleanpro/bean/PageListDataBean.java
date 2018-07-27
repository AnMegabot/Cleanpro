package com.pakpobox.cleanpro.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/27
 * Time:18:01
 */

public class PageListDataBean<T> {
    private int page;
    private int maxCount;
    private int totalPage;
    private int totalCount;
    private List<T> resultList = new ArrayList<>();

    public boolean isOver() {
        return page >= totalPage-1;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
