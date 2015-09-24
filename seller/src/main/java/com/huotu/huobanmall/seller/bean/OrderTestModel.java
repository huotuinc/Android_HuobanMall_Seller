package com.huotu.huobanmall.seller.bean;

import java.util.Date;

/**
 * Created by Administrator on 2015/9/21.
 */
public class OrderTestModel {
    private String childOrderNO;
    private String mainOrderNO;
    private String status;
    private Date time;
    private String pictureUrl;
    private String goodsName;
    private Float price;
    private Integer count;
    private Float totalPrice;

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
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
