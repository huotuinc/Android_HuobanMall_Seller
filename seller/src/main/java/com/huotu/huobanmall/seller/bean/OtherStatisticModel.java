package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/11.
 */
public class OtherStatisticModel {
    private Integer goodsAmount;
    private Integer discributorAmount;
    private Integer memberAmount;
    private Integer billAmount;

    public void setBillAmount(Integer billAmount) {
        this.billAmount = billAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public void setDiscributorAmount(Integer discributorAmount) {
        this.discributorAmount = discributorAmount;
    }

    public void setMemberAmount(Integer memberAmount) {
        this.memberAmount = memberAmount;
    }

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public Integer getDiscributorAmount() {
        return discributorAmount;
    }

    public Integer getMemberAmount() {
        return memberAmount;
    }

    public Integer getBillAmount() {
        return billAmount;
    }
}
