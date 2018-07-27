package com.pakpobox.cleanpro.bean.price;

import java.util.List;

/**
 * 价格
 * User:Sean.Wei
 * Date:2018/7/27
 * Time:10:47
 */

public class Price {
    private String id;
    private String name;
    private String name_en;
    private double price;
    private List<Sku> sku_list;
    private List<ItemProp> item_props;
    private Provider provider;
    private int quantity;
    private String status;

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

    public List<Sku> getSku_list() {
        return sku_list;
    }

    public void setSku_list(List<Sku> sku_list) {
        this.sku_list = sku_list;
    }

    public List<ItemProp> getItem_props() {
        return item_props;
    }

    public void setItem_props(List<ItemProp> item_props) {
        this.item_props = item_props;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
