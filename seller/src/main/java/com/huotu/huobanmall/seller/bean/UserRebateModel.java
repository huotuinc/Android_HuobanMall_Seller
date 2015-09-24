package com.huotu.huobanmall.seller.bean;

import java.util.Date;

/**
 * Created by Administrator on 2015/9/24.
 */
public class UserRebateModel {
    private Date getTime;
    private String present;
    private Date Regularization;
    private Integer score;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public Date getRegularization() {
        return Regularization;
    }

    public void setRegularization(Date regularization) {
        Regularization = regularization;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


}
