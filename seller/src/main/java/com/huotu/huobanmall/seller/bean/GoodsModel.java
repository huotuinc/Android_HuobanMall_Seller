package com.huotu.huobanmall.seller.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/8/27.
 * 商品列表实体类
 */
public class GoodsModel implements Serializable {
    /**
     *
     */
    private Integer goodsId;
    /**
     * 图片地址
     */
    private String pictureUrl;
    /**
     * 销售价格(元)
     */
    private double price;
    /**
     * 库存量
     */
    private int stock;
    /**
     * 商品标题
     */
    private String title;
    /**
     *
     */
    private boolean selected;
    /**
     *
     */
    private int viewType;

    /**
     *
     * 产品分类
     */
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public double getPrice() {
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

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
