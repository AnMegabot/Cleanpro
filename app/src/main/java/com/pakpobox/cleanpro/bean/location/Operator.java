package com.pakpobox.cleanpro.bean.location;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/31
 * Time:11:42
 */

public class Operator {
    private String id;
    private String name;
    private String companyType;
    private int deleteFlag;
    private int level;
    private List<String> contactPhoneNumber;
    private List<String> contactEmail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<String> getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(List<String> contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public List<String> getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(List<String> contactEmail) {
        this.contactEmail = contactEmail;
    }
}
