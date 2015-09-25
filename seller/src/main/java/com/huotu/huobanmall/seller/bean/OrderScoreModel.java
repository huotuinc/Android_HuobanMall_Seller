package com.huotu.huobanmall.seller.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 */
public class OrderScoreModel {
    private String userType;
    private Date getTime;
    private String status;
    private Date zzTime;
    private Integer score;
    private String userName;
    private int Type;
    private List<OrderScoreModel> list;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<OrderScoreModel> getList() {
        return list;
    }

    public void setList(List<OrderScoreModel> list) {
        this.list = list;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }


    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getZzTime() {
        return zzTime;
    }

    public void setZzTime(Date zzTime) {
        this.zzTime = zzTime;
    }


}
