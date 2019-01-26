package com.pakpobox.cleanpro.bean;

/**
 * User:Sean.Wei
 * Date:2019/1/26
 * Time:10:19
 */

public class FeedbackReq {
    private String loginName;
    private String type;
    private String content;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
