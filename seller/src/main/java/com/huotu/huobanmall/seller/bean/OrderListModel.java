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
    private double paid;
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
    private String status;
    /**
     * 下单时间
     */
    private Date time;
    /**
     * 规格数据
     */
    private List<OrderListProductModel> list;
    private List<OrderListModel> childOrders;
    private boolean hasChildOrder =false;

    private String title;
    /**
     * 主订单号
     */
    private String mainOrderNo;

    public String getMainOrderNo() {
        return mainOrderNo;
    }

    public void setMainOrderNo(String mainOrderNo) {
        this.mainOrderNo = mainOrderNo;
    }

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



    public List<OrderListProductModel> getList() {
        return list;
    }

    public void setList(List<OrderListProductModel> list) {
        this.list = list;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public double getPaid() {
        return this.paid;
    }

    public void setPaid(double paid) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
