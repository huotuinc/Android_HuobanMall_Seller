package com.huotu.huobanmall.seller.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class OrderListModel {
    /**
     * 商品数量
     */
    private Integer amount;
    /**
     * 实付金额
     */
    private Float paid;
    /**
     * 订单号
     */
    private String orderNo;
    private String pictureUrl;
    private String receiver;
    /**
     * 返利积分
     */
    private Integer score;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 下单时间
     */
    private Date time;
    /**
     * 规格数据
     */
    private List<GoodsModel> list;
    private List<OrderListModel> childOrders;
    private boolean hasChildOrder =false;

    private String title;

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



    public List<GoodsModel> getList() {
        return list;
    }

    public void setList(List<GoodsModel> list) {
        this.list = list;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Float getPaid() {
        return this.paid;
    }

    public void setPaid(Float paid) {
        this.paid = paid;
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



}
