package com.huotu.huobanmall.seller.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/22.
 */
public class OrderDetailModel {
    /**
     * 地址
     */
    private String address;
    /**
     * 商品数量
     */
    private Integer amount;
    /**
     * 购买人
     */
    private String buyer;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 规格数据
     */
    private List<OrderListProductModel> list;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 实付金额
     */
    private Float paid;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 返利积分
     */
    private List<UserScoreModel> scoreList;

    public List<UserScoreModel> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<UserScoreModel> scoreList) {
        this.scoreList = scoreList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<OrderListProductModel> getList() {
        return list;
    }

    public void setList(List<OrderListProductModel> list) {
        this.list = list;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Float getPaid() {
        return paid;
    }

    public void setPaid(Float paid) {
        this.paid = paid;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
