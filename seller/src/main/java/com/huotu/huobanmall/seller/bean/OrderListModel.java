package com.huotu.huobanmall.seller.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class OrderListModel {

    private Integer amount;
    private Float money;
    private String orderNo;
    private String pictureUrl;
    private String receiver;
    private Integer score;
    private Integer status;
    private Date time;
    private List<GoodsModel> goods;
    private List<OrderListModel> childOrders;
    private boolean hasChildOrder =false;

    public List<OrderListModel> getChildOrders() {
        return childOrders;
    }

    public void setChildOrders(List<OrderListModel> childOrders) {
        this.childOrders = childOrders;
    }

    public boolean getHasChildOrder() {
        return hasChildOrder;
    }

    public void setHasChildOrder(boolean hasChildOrder) {
        this.hasChildOrder = hasChildOrder;
    }



    public List<GoodsModel> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsModel> goods) {
        this.goods = goods;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

}
