package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/18.
 * 商品销售前10排名
 */
public class TopSalesModel {

    /**
     * 单价
     */
    private Double money;
    /**
     * 图片地址
     */
    private String pictureUrl;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 订单号
     */
    private String orderNo;


    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }


}
