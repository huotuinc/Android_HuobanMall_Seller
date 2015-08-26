package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/8/24.
 */
public class PurchaseOfGoods {
    public void setName(String name) {
        this.name = name;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    private String name;
    private String counts;

    public String getName() {
        return name;
    }

    public String getCounts() {
        return counts;
    }
}
