package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/23.
 */
public class TopGoodsModel {
    private Integer amount;
    private String picture;
    private Double price;
    private String title;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
