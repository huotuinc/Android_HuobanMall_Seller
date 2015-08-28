package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/8/27.
 * 商品列表实体类
 */
public class GoodsModel {
    public String getPictureUrl() {
        return pictureUrl;
    }

    public float getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getTitle() {
        return title;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 图片地址
     */
    private String pictureUrl;
    /**
     * 销售价格(元)
     */
    private float price;
    /**
     * 库存量
     */
    private int stock;
    /**
     * 商品标题
     */
    private String title;
}
