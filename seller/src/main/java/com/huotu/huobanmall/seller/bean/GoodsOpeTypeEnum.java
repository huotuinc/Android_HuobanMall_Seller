package com.huotu.huobanmall.seller.bean;

import android.graphics.Color;

import org.w3c.dom.ProcessingInstruction;

/**
 * Created by Administrator on 2015/8/27.
 */
public enum  GoodsOpeTypeEnum {
    //1 上架商品 2 下架商品 3 删除商品
    ONSHELF("上架商品",1),
    OFFSHELF("下架商品",2),
    DELETEGOODS("删除商品",3);

    private GoodsOpeTypeEnum(String name , int index){
        this.name=name;
        this.index=index;
    }
    // 普通方法
    public static String getName(int index) {
        for (GoodsOpeTypeEnum c : GoodsOpeTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    private String name;

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int index;




}
