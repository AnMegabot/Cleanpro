package com.pakpobox.cleanpro.bean;

/**
 * User:Sean.Wei
 * Date:2019/1/26
 * Time:10:40
 */

public class Feedback {
    private String id;
    private String content;
    private String loginName;
    private long create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
}
