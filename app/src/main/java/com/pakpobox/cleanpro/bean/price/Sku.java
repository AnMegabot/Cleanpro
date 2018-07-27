package com.pakpobox.cleanpro.bean.price;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/27
 * Time:10:49
 */

public class Sku {
    private String id;
    private String name;
    private String name_en;
    private double price;
    private String price_type;
    private List<PropValue> prop_values;

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

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        this.price_type = price_type;
    }

    public List<PropValue> getProp_values() {
        return prop_values;
    }

    public void setProp_values(List<PropValue> prop_values) {
        this.prop_values = prop_values;
    }
}
