package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/18.
 * 商品销售前10排名
 */
public class TopSalesModel {
    /**
     * 购买单数
     */
    private Integer amount;
    /**
     * 单价
     */
    private Float price;
    /**
     * 商品图片地址
     */
    private String pictureUrl;
    /**
     * 商品名称
     */
    private String name;


    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
