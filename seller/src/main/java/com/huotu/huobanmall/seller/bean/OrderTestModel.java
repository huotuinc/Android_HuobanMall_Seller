package com.huotu.huobanmall.seller.bean;

import java.util.Date;

/**
 * Created by Administrator on 2015/9/21.
 */
public class OrderTestModel {
    private String childOrderNO;
    private String mainOrderNO;
    private String status;
    private String orderTime;
    private String pictureUrl;
    private String goodsName;
    private String price;
    private String count;
    private String totalPrice;

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    private String spec;

    public String getChildOrderNO() {
        return childOrderNO;
    }

    public void setChildOrderNO(String childOrderNO) {
        this.childOrderNO = childOrderNO;
    }

    public String getMainOrderNO() {
        return mainOrderNO;
    }

    public void setMainOrderNO(String mainOrderNO) {
        this.mainOrderNO = mainOrderNO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    private int viewType;
}
