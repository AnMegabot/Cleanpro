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
    private int continue_value;
    private double continue_price;
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

    public int getContinue_value() {
        return continue_value;
    }

    public void setContinue_value(int continue_value) {
        this.continue_value = continue_value;
    }

    public double getContinue_price() {
        return continue_price;
    }

    public void setContinue_price(double continue_price) {
        this.continue_price = continue_price;
    }

    public List<PropValue> getProp_values() {
        return prop_values;
    }

    public void setProp_values(List<PropValue> prop_values) {
        this.prop_values = prop_values;
    }
}
