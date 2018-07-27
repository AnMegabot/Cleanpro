package com.pakpobox.cleanpro.bean.price;

/**
 * User:Sean.Wei
 * Date:2018/7/27
 * Time:10:52
 */

public class ItemProp {
    private String id;
    private String name;
    private String alias_name;
    private boolean is_sku_prop;
    private String remark;

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

    public String getAlias_name() {
        return alias_name;
    }

    public void setAlias_name(String alias_name) {
        this.alias_name = alias_name;
    }

    public boolean isIs_sku_prop() {
        return is_sku_prop;
    }

    public void setIs_sku_prop(boolean is_sku_prop) {
        this.is_sku_prop = is_sku_prop;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
