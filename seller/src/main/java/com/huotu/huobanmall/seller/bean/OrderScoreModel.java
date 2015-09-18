package com.huotu.huobanmall.seller.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 */
public class OrderScoreModel {
    private String orderNo;
    private Long getTime;
    private String status;
    private Long zzTime;

    public List<OrderScoreModel> getList() {
        return list;
    }

    public void setList(List<OrderScoreModel> list) {
        this.list = list;
    }

    private List<OrderScoreModel> list;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    private int Type;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getGetTime() {
        return getTime;
    }

    public void setGetTime(Long getTime) {
        this.getTime = getTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getZzTime() {
        return zzTime;
    }

    public void setZzTime(Long zzTime) {
        this.zzTime = zzTime;
    }

    private Integer score;
}
