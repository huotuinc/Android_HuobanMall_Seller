package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/10.
 */
public enum OperateTypeEnum {
    REFRESH("刷新",1),
    LOAD("加载更多",2);

    private OperateTypeEnum(String name , int index){
        this.name=name;
        this.index=index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private String name;
    private int index;
}
